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
package eu.clarin.toolportal.ui.service.filter;

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
public class RecordDescriptionFilterTest {

    /**
     * Test of filterValue method, of class RecordDescriptionFilter.
     */
    @Test
    public void testFilterValue() {
        RecordDescriptionFilter instance = new RecordDescriptionFilter();
        assertEquals(instance.filterValue("test123"), "test123");
        assertEquals(instance.filterValue("{code:eng}test123"), "test123");
        assertEquals(instance.filterValue("{code:nld}test123"), "test123");
        assertEquals(instance.filterValue("{code:nl}test123"), "test123");
        assertEquals(instance.filterValue("{code:n}test123"), "test123");
        assertEquals(instance.filterValue("{code:}test123"), "test123");
        assertEquals(instance.filterValue("{code}test123"), "{code}test123");
        assertEquals(instance.filterValue("{code123}test123"), "{code123}test123");
    }
    
}
