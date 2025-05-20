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
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author twagoo
 */
public class FilterQueryFacetSelectionConverterTest {

    public FilterQueryFacetSelectionConverterTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of filterQueryToFacetMap method, of class
     * FilterQueryFacetSelectionConverter.
     */
    @Test
    public void testFilterQueryToFacetMap() {
        List<String> filterQuery = ImmutableList.of(
                "facetA:value1",
                "facetB:value2",
                "facetB:value3",
                "facetC:value:4");
        FilterQueryHelper instance = new FilterQueryHelper();
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
        FilterQueryHelper instance = new FilterQueryHelper();
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

}
