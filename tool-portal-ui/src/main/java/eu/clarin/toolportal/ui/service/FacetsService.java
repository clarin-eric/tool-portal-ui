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
package eu.clarin.toolportal.ui.service;

import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import eu.clarin.cmdi.vlo.openapi.client.api.FacetsApi;
import eu.clarin.cmdi.vlo.openapi.client.model.Facet;
import eu.clarin.cmdi.vlo.openapi.client.model.ValueCount;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author twagoo
 */
@Service
public class FacetsService {

    public static final Joiner FACET_VALUE_JOINER = Joiner.on(":");

    private final FacetsApi service;

    public FacetsService(FacetsApi facets) {
        this.service = facets;
    }

    /**
     * *
     * Retrieves facet information relative to query/filter
     *
     * @param query records query to apply
     * @param filterQueries records filter to apply
     * @param valueCountLimit
     * @return
     */
    public List<Facet> getFacets(String query, List<String> filterQueries, int valueCountLimit) {
        return service.getFacets(query, filterQueries, null, valueCountLimit);
    }

    /**
     * *
     * Retrieves selective facet information relative to query/filter
     *
     * @param query records query to apply
     * @param filterQueries records filter to apply
     * @param includeFacets facets to include
     * @param valueCountLimit
     * @return
     */
    public List<Facet> getFacets(String query, List<String> filterQueries, List<String> includeFacets, int valueCountLimit) {
        //get facets
        final List<Facet> facets = service.getFacets(query, filterQueries, includeFacets, valueCountLimit);
        //apply include filter (also applies sorting of filter)
        return applyIncludeFilter(facets, includeFacets);
    }

    /**
     * *
     * Retrieves facet values for a facet taking into account the active
     * query/filter
     *
     * @param facetName name of facet to get values for
     * @param query records query to apply
     * @param filterQueries records filter to apply
     * @param from pagination: start
     * @param valueCountLimit pagination: size
     * @return
     */
    public Facet getFacet(String facetName, String query, List<String> filterQueries, int from, int valueCountLimit) {

        final ResponseEntity<Facet> apiResponse = service.getFacetWithHttpInfo(facetName, query, filterQueries, valueCountLimit);
        if (apiResponse.getStatusCode().isError()) {
            if (apiResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
                //facet not found
                return null;
            } else {
                throw new RuntimeException("Error response from API while getting facet values for "
                        + facetName
                        + ": "
                        + apiResponse.getStatusCode());
            }
        } else {

            final Facet facet = apiResponse.getBody();
            if (facet != null) {
                FluentIterable<ValueCount> paginated = FluentIterable.from(facet.getValues());
                if (from > 0) {
                    paginated = paginated.skip(from);
                }
                facet.setValues(paginated.limit(valueCountLimit).toList());
            }

            return facet;
        }

    }

    /**
     *
     * @param facets all facets
     * @param includeFacets names of facets to include
     * @return the subset of the facets that appear in the include filter,
     * sorted according to the latter
     */
    private List<Facet> applyIncludeFilter(final List<Facet> facets, List<String> includeFacets) {
        return facets.stream()
                //filter out values not in include list
                .filter(facet -> includeFacets.contains(facet.getName()))
                //sort in order of appearance in the include filter
                .sorted(Comparator.comparingInt(facet -> includeFacets.indexOf(facet.getName())))
                //collect
                .toList();
    }
}
