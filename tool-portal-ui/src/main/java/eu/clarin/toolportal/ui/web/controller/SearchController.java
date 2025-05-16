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
import eu.clarin.cmdi.vlo.openapi.client.model.Facet;
import eu.clarin.cmdi.vlo.openapi.client.model.VloRecordSearchResult;
import eu.clarin.toolportal.ui.service.FacetsService;
import eu.clarin.toolportal.ui.service.RecordsService;
import static eu.clarin.toolportal.ui.web.HtmxUtils.isHtmxRequest;
import static eu.clarin.toolportal.ui.web.HtmxUtils.isHtmxTarget;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private List<String> facetsFilter = ImmutableList.of("resourceClass", "languageCode", "keyword");

    public SearchController(RecordsService recordService, FacetsService facetsService) {
        this.recordsService = recordService;
        this.facetsService = facetsService;
    }

    @GetMapping
    public String index(Model model,
            @RequestParam(name = "q", defaultValue = DEFAULT_QUERY) String query,
            @RequestParam(name = "from", defaultValue = DEFAULT_FROM) Integer from,
            @RequestParam(name = "size", defaultValue = DEFAULT_SIZE) Integer size,
            @RequestHeader Map<String, String> headers) {
        // Query record service 
        // TODO: use search service once available
        final VloRecordSearchResult result = recordsService.getRecords(query, Collections.emptyList(), from, size);

        // Set model attributes
        model.addAttribute("result", result);
        model.addAttribute("query", query);
        model.addAttribute("resultsPerPage", size);
        model.addAttribute("pageCount", Math.ceilDiv(result.getNumFound(), size));
        model.addAttribute("currentPage", 1 + (from / size));

        // Determine response content
        if (isHtmxRequest(headers)) {
            // HTMX: partial response
            if (isHtmxTarget(headers, "search-results")) {
                //request from pagination, return only search results
                return "search/search :: #search-results";
            } else {
                //return search results and facets
                addFacetsToModel(model, query);
                return "search/search :: #search-results-and-facets";
            }
        } else {
            //return entire page
            addFacetsToModel(model, query);

            return "search/search";
        }
    }

    public Model addFacetsToModel(Model model, String query) {
        return model.addAttribute("facets", getFilteredFacets(query));
    }

    public List<Facet> getFilteredFacets(String query) {
        return facetsService.getFacets(query, Collections.emptyList(), facetsFilter);
    }

}
