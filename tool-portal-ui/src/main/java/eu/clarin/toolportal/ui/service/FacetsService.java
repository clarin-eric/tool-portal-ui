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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import eu.clarin.cmdi.vlo.openapi.client.api.FacetsApi;
import eu.clarin.cmdi.vlo.openapi.client.model.Facet;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
     * @param filterMap records filter to apply
     * @return
     */
    public List<Facet> getFacets(String query, Map<String, Collection<String>> filterMap) {
        return service.getFacets(query, mapToFilters(filterMap));
    }

    /**
     * *
     * Retrieves selective facet information relative to query/filter
     *
     * @param query records query to apply
     * @param filterMap records filter to apply
     * @param includeFacets facets to include
     * @return
     */
    public List<Facet> getFacets(String query, Map<String, Collection<String>> filterMap, List<String> includeFacets) {
        //get facets
        final List<Facet> facets = getFacets(query, filterMap);
        //apply include filter
        return applyIncludeFilter(facets, includeFacets);
    }

    public List<Facet> applyIncludeFilter(final List<Facet> facets, List<String> includeFacets) {
        return facets.stream()
                //filter out values not in include list
                .filter(facet -> includeFacets.contains(facet.getName()))
                //sort in order of appearance in the include filter
                .sorted(Comparator.comparingInt(facet -> includeFacets.indexOf(facet.getName())))
                //collect
                .toList();
    }

    private List<String> mapToFilters(Map<String, Collection<String>> filterMap) {
        final ImmutableList.Builder<String> list = ImmutableList.builderWithExpectedSize(filterMap.size());
        filterMap.forEach((facet, values) -> {
            list.addAll(Iterables.transform(values,
                    value -> FACET_VALUE_JOINER.join(facet, value)));
        });
        return list.build();
    }

}
