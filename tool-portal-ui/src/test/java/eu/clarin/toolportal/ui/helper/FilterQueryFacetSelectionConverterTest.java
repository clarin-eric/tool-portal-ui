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
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        System.out.println("filterQueryToFacetMap");
        List<String> filterQuery = ImmutableList.of(
                "facetA:value1",
                "facetB:value2",
                "facetB:value3",
                "facetC:value4");
        FilterQueryFacetSelectionConverter instance = new FilterQueryFacetSelectionConverter();
        Map<String, List<String>> result = instance.filterQueryToFacetMap(filterQuery);
        assertThat(result).isNotNull();
        assertThat(result).hasSize(4);
        assertThat(result).containsKeys("facetA", "facetB", "facetC");
    }

}
