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
package eu.clarin.toolportal.ui.configuration;

import eu.clarin.toolportal.ui.service.filter.RecordDescriptionFilter;
import eu.clarin.toolportal.ui.service.filter.RecordFilter;
import eu.clarin.toolportal.ui.service.filter.RecordFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author twagoo
 */
@Configuration
public class ContentFiltersConfiguration {

    @Bean
    public RecordFilter filterChain() {
        final RecordFilterChain chain = new RecordFilterChain();
        chain.addFilter(new RecordDescriptionFilter());
        return chain;
    }

}
