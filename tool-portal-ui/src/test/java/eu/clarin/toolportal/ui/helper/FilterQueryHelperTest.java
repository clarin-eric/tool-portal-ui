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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author twagoo
 */
public class FilterQueryHelperTest {

    private FilterQueryHelper instance;

    @BeforeEach
    public void setUp() {
        instance = new FilterQueryHelper();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testFilterQueryToFacetMap() {
        List<String> filterQuery = ImmutableList.of(
                "facetA:value1",
                "facetB:value2",
                "facetB:value3",
                "facetC:value:4");
        Multimap<String, String> result = instance.filterQueryToFacetMap(filterQuery);
        assertThat(result).isNotNull();
        assertThat(result.asMap()).hasSize(3);
        assertThat(result.asMap()).containsKeys("facetA", "facetB", "facetC");
        assertThat(result.asMap()).extractingByKey("facetA")
                .isInstanceOf(Collection.class)
                .matches(c -> c.size() == 1)
                .matches(c -> c.contains("value1"));
        assertThat(result.asMap()).extractingByKey("facetB")
                .isInstanceOf(Collection.class)
                .matches(c -> c.size() == 2)
                .matches(c -> c.contains("value2"))
                .matches(c -> c.contains("value3"));
        assertThat(result.asMap()).extractingByKey("facetC")
                .isInstanceOf(Collection.class)
                .matches(c -> c.size() == 1)
                .matches(c -> c.contains("value:4"));
    }

    @Test
    public void testSkipErroneousValuesMap() {
        List<String> filterQuery = ImmutableList.of(
                "facetA:value1",
                "facetBvalue2",
                "",
                "facetD:value3",
                "facetE:",
                ":value4");
        Multimap<String, String> result = instance.filterQueryToFacetMap(filterQuery);
        assertThat(result).isNotNull();
        assertThat(result.asMap()).hasSize(2);
        assertThat(result.asMap()).containsKeys("facetA", "facetD");
        assertThat(result.asMap()).extractingByKey("facetA")
                .isInstanceOf(Collection.class)
                .matches(c -> c.size() == 1)
                .matches(c -> c.contains("value1"));

        assertThat(result.asMap()).extractingByKey("facetD")
                .isInstanceOf(Collection.class)
                .matches(c -> c.size() == 1)
                .matches(c -> c.contains("value3"));
    }

    @Test
    public void testAddToMap() {
        final Multimap<String, String> filterQueryMap
                = ImmutableListMultimap.<String, String>builder()
                        .putAll("facetA", "value1", "value2")
                        .put("facetB", "value3")
                        .build();

        final List<String> addToFilterQuery
                = ImmutableList.of("facetB:value4", "facetB:value5", "facetC:value6");
        final Multimap<String, String> result = instance.addToMap(filterQueryMap, addToFilterQuery);
        assertThat(result).isNotNull();
        assertThat(result.keySet()).hasSize(3);
        assertThat(result.asMap()
        ).extractingByKey("facetA")
                .isInstanceOf(Collection.class)
                .matches(c -> c.size() == 2)
                .matches(c -> c.contains("value1"))
                .matches(c -> c.contains("value2"));
        assertThat(result.asMap()
        ).extractingByKey("facetB")
                .isInstanceOf(Collection.class)
                .matches(c -> c.size() == 3)
                .matches(c -> c.contains("value3"))
                .matches(c -> c.contains("value4"))
                .matches(c -> c.contains("value5"));
        assertThat(result.asMap()
        ).extractingByKey("facetC")
                .isInstanceOf(Collection.class)
                .matches(c -> c.size() == 1)
                .matches(c -> c.contains("value6"));
    }

}
