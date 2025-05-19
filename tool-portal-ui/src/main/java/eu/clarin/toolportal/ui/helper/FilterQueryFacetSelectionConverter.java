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
import com.google.common.collect.Multimap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 *
 * @author twagoo
 */
@Component
public class FilterQueryFacetSelectionConverter {

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

}
