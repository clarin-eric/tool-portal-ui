/*
 * Copyright (C) 2025 twagoo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.clarin.toolportal.ui.web.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import eu.clarin.cmdi.vlo.openapi.client.model.Facet;
import eu.clarin.cmdi.vlo.openapi.client.model.VloRecordSearchResult;
import eu.clarin.toolportal.ui.configuration.SearchConfigurationProperties;
import eu.clarin.toolportal.ui.helper.FilterQueryHelper;
import eu.clarin.toolportal.ui.service.FacetsService;
import static eu.clarin.toolportal.ui.service.FacetsService.FACET_VALUE_JOINER;
import eu.clarin.toolportal.ui.service.RecordsService;
import eu.clarin.toolportal.ui.service.filter.RecordFilter;
import static eu.clarin.toolportal.ui.web.HtmxUtils.isHtmxRequest;
import static eu.clarin.toolportal.ui.web.HtmxUtils.isHtmxTarget;
import static eu.clarin.toolportal.ui.web.HtmxUtils.pushUrl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author twagoo
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {

    public static final String DEFAULT_QUERY = "";
    public static final String DEFAULT_FROM = "0";
    public static final String DEFAULT_SIZE = "10";

    private final RecordsService recordsService;
    private final FacetsService facetsService;
    private final FilterQueryHelper filterQueryHelper;

    private final SearchConfigurationProperties searchConfig;
    private final RecordFilter recordFilter;
    private final List<String> allFacets;

    public SearchController(RecordsService recordService, FacetsService facetsService, FilterQueryHelper filterQueryConverter, SearchConfigurationProperties searchConfig, RecordFilter recordFilter) {
        this.recordsService = recordService;
        this.facetsService = facetsService;
        this.filterQueryHelper = filterQueryConverter;
        this.searchConfig = searchConfig;
        this.recordFilter = recordFilter;
        this.allFacets = ImmutableList.copyOf(Iterables.concat(
                searchConfig.getPrimaryFacets()
        //TODO: add other (non-primary) facets
        ));
    }

    @GetMapping
    public List<ModelAndView> index(Model model,
            @RequestParam(name = "q", defaultValue = DEFAULT_QUERY) String query,
            @RequestParam(name = "fq", defaultValue = "") List<String> filterQuery,
            @RequestParam(name = "addfq", defaultValue = "") List<String> addToFilterQuery,
            @RequestParam(name = "removefq", defaultValue = "") List<String> removeFromFilterQuery,
            @RequestParam(name = "from", defaultValue = DEFAULT_FROM) Integer from,
            @RequestParam(name = "size", defaultValue = DEFAULT_SIZE) Integer size,
            @RequestHeader Map<String, String> headers,
            HttpServletRequest request,
            HttpServletResponse response) {
        final Map<String, Collection<String>> filterQueryMap
                = constructFilterQueryMap(filterQuery, addToFilterQuery, removeFromFilterQuery);

        // Query record service
        // TODO: use search service once available
        final List<String> searchFilters = flattenFilterMap(filterQueryMap);
        final VloRecordSearchResult result
                = RecordsService.applyFilter(recordsService.getRecords(query, searchFilters, from, size), recordFilter);

        // Set model attributes
        model.addAttribute("result", result);
        model.addAttribute("query", query);
        model.addAttribute("filterQueryMap", filterQueryMap);
        model.addAttribute("searchFilters", searchFilters);
        model.addAttribute("resultsPerPage", size);
        model.addAttribute("pageCount", Math.ceilDiv(result.getNumFound(), size));
        model.addAttribute("currentPage", 1 + (from / size));

        // Determine response content
        if (isHtmxRequest(headers)) {
            // HTMX: partial response
            // send clean URL to client (apply fq updates)
            sanitiseUrl(request, searchFilters)
                    .ifPresent(url -> pushUrl(response, url));
            if (isHtmxTarget(headers, "search-results")) {
                //request from pagination, return only search results
                return ImmutableList.of(new ModelAndView("search/search :: #search-results"),
                        new ModelAndView("search/search :: #breadcrumbs"));
            } else {
                //return search results and facets
                addFacetsToModel(model, query, searchFilters);
                return ImmutableList.of(new ModelAndView("search/search :: #search-results-and-facets"),
                        new ModelAndView("search/search :: #breadcrumbs"));
            }
        } else {
            //return entire page
            addFacetsToModel(model, query, searchFilters);

            return ImmutableList.of(new ModelAndView("search/search"));
        }
    }

    @GetMapping("/values/{facetName}")
    public ModelAndView facetValues(
            @PathVariable("facetName") String facetName,
            @RequestParam(name = "q", defaultValue = DEFAULT_QUERY) String query,
            @RequestParam(name = "fq", defaultValue = "") List<String> filterQuery,
            @RequestParam(name = "from", defaultValue = DEFAULT_FROM) Integer from,
            @RequestParam(name = "size", defaultValue = DEFAULT_SIZE) Integer size,
            @RequestHeader Map<String, String> headers,
            HttpServletRequest request,
            HttpServletResponse response) {

        final ModelAndView modelAndView = new ModelAndView("search/facetValues");

        if (facetExists(facetName)) {
            final Facet facet = facetsService.getFacet(facetName, query, filterQuery, from, size);
            final Map<String, Collection<String>> filterQueryMap
                    = constructFilterQueryMap(filterQuery, null, null);
            final List<String> searchFilters = flattenFilterMap(filterQueryMap);

            modelAndView.addObject("facet", facet);

            modelAndView.addObject("query", query);
            modelAndView.addObject("filterQueryMap", filterQueryMap);
            modelAndView.addObject("searchFilters", searchFilters);
            modelAndView.addObject("size", size);

        } else {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        }
        return modelAndView;

    }

    public boolean facetExists(String facetName) {
        return allFacets.stream().anyMatch(s
                -> s.equalsIgnoreCase(facetName));
    }

    private Map<String, Collection<String>> constructFilterQueryMap(List<String> filterQuery, List<String> addToFilterQuery, List<String> removeFromFilterQuery) {
        Multimap<String, String> filterQueryMap
                = filterQueryHelper.filterQueryToFacetMap(filterQuery);
        if (addToFilterQuery != null) {
            filterQueryMap = filterQueryHelper.addToMap(filterQueryMap, addToFilterQuery);
        }
        if (removeFromFilterQuery != null) {
            filterQueryMap = filterQueryHelper.removeFromMap(filterQueryMap, removeFromFilterQuery);
        }
        return filterQueryMap.asMap();
    }

    private Model addFacetsToModel(Model model, String query, List<String> filterQueries) {
        //get facet
        final List<Facet> facets = facetsService.getFacets(query, filterQueries, searchConfig.getPrimaryFacets(), searchConfig.getFacetValuesShown());
        return model.addAttribute("facets", facets);
    }

    /**
     * Flattens filter map to a list of 'facet:value' strings
     *
     * @param filterMap map to flatten
     * @return list of 'facet:value' strings for each key - value item pair
     */
    private List<String> flattenFilterMap(Map<String, Collection<String>> filterMap) {
        return filterMap.entrySet().stream()
                .flatMap(e -> e.getValue().stream()
                .map(value -> FACET_VALUE_JOINER.join(e.getKey(), value)))
                .toList();
    }

    /**
     * Creates a nicer URL that has all filter queries in the 'fq' parameter
     *
     * @param request
     * @param searchFilters
     * @return
     */
    private Optional<String> sanitiseUrl(HttpServletRequest request, List<String> searchFilters) {
        final String requestURL = request.getRequestURL().toString();
        final String queryString = request.getQueryString();

        try {
            final URI url = new URI(requestURL + "?" + queryString);
            url.toURL(); // this throws a MalformedURLException if the URL is not valid

            final UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url);
            builder.replaceQueryParam("addfq", Collections.emptySet());
            builder.replaceQueryParam("removefq", Collections.emptySet());
            builder.replaceQueryParam("fq", searchFilters);

            return Optional.of(builder.build().toUriString());
        } catch (MalformedURLException | URISyntaxException ex) {
            return Optional.empty();
        }
    }

}
