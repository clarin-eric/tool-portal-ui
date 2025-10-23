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

import com.google.common.collect.Lists;
import java.util.Locale;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;

/**
 *
 * @author twagoo
 */
public class FacetValueServiceTest {

    /**
     * Test of getLabel method, of class FacetValueService.
     */
    @Test
    public void testGetLabelFromStaticMessageSource() {
        final StaticMessageSource messageSource1 = new StaticMessageSource();
        messageSource1.addMessage("facetValues.test1.value1", Locale.ROOT, "message1.1");
        messageSource1.addMessage("facetValues.test1.value2", Locale.ROOT, "message1.2");

        final StaticMessageSource messageSource2 = new StaticMessageSource();
        messageSource2.addMessage("facetValues.test2.value1", Locale.ROOT, "message2.1");
        messageSource2.addMessage("facetValues.test2.value2", Locale.ROOT, "message2.2");

        FacetValueService instance = new FacetValueService(
                Lists.newArrayList(
                        messageSource1, messageSource2));

        assertThat(instance.getLabel("test1", "value1", Locale.ROOT),
                equalTo("message1.1"));
        assertThat(instance.getLabel("test1", "value2", Locale.ROOT),
                equalTo("message1.2"));
        assertThat(instance.getLabel("test2", "value1", Locale.ROOT),
                equalTo("message2.1"));
        assertThat(instance.getLabel("test2", "value2", Locale.ROOT),
                equalTo("message2.2"));

        assertThat("No matching code -> original value",
                instance.getLabel("test3", "original value", Locale.ROOT),
                equalTo("original value"));
        assertThat("No matching locale -> original value",
                instance.getLabel("test1", "original value", Locale.FRENCH),
                equalTo("original value"));

    }

}
