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
package eu.clarin.toolportal.ui.helper;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

/**
 *
 * @author twagoo
 */
@Component
public class FilterQueryHelper {

    public static final Splitter SPLITTER = Splitter.on(':').trimResults();
    public static final Joiner JOINER = Joiner.on(":");

    public Multimap<String, String> filterQueryToFacetMap(List<String> filterQuery) {
        final ImmutableListMultimap.Builder<String, String> map
                = ImmutableListMultimap.builderWithExpectedKeys(filterQuery.size());
        filterQuery.forEach(fq -> {
            entryFor(fq).ifPresent(map::put);
        });
        return map.build();
    }

    private Optional<Map.Entry<String, String>> entryFor(String fq) {
        Iterator<String> iterator = SPLITTER.split(fq).iterator();
        final String key = iterator.next();
        if (!Strings.isNullOrEmpty(key) && iterator.hasNext()) {
            final String value = JOINER.join(iterator);
            if (!Strings.isNullOrEmpty(value)) {
                return Optional.of(Map.entry(key, value));
            }
        }
        return Optional.empty();
    }

    public Multimap<String, String> addToMap(Multimap<String, String> filterQueryMap, List<String> addToFilterQuery) {
        if (addToFilterQuery.isEmpty()) {
            //add nothing -> return the existing map
            return filterQueryMap;
        } else {
            if (filterQueryMap.isEmpty()) {
                //add to nothing -> just return the new entries as a map
                return filterQueryToFacetMap(addToFilterQuery);
            } else {
                //build a fresh multimap that includes the new entires
                return ImmutableListMultimap.<String, String>builderWithExpectedKeys(
                        filterQueryMap.keySet().size() + addToFilterQuery.size())
                        //add existing filter query map
                        .putAll(filterQueryMap)
                        //add all valid new filters
                        .putAll(addToFilterQuery.stream()
                                .map(this::entryFor)
                                .flatMap(Optional::stream).toList())
                        .build();
            }
        }
    }

    public Multimap<String, String> removeFromMap(Multimap<String, String> filterQueryMap, List<String> removeFromFilterQuery) {
        //TODO: implement
        List<Map.Entry<String, String>> entriesToRemove = removeFromFilterQuery.stream()
                .map(this::entryFor)
                .flatMap(Optional::stream)
                .toList();
        
        return Multimaps.filterEntries(filterQueryMap, entry -> !entriesToRemove.contains(entry));
    }

}
