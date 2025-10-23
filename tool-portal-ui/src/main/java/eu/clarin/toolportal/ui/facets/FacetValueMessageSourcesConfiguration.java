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
package eu.clarin.toolportal.ui.facets;

import com.google.common.collect.Streams;
import eu.clarin.toolportal.ui.service.FacetValueService;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 *
 * @author twagoo
 */
@Configuration
public class FacetValueMessageSourcesConfiguration {

    @Autowired
    private FacetValueProperties facetValuePropertiesConfiguration;


    @Autowired
    private ApplicationContext ctx;

    @Bean
    public FacetValueService facetValueService() {
        final ReloadableResourceBundleMessageSource resourceBundlesMessageSource = new ReloadableResourceBundleMessageSource();
        final String[] basenames = Stream.of(
                Optional.ofNullable(facetValuePropertiesConfiguration.getInternalResourceBundleBasenames()),
                Optional.ofNullable(facetValuePropertiesConfiguration.getResourceBundleBasenames()))
                .flatMap(Optional::stream)
                .flatMap(List::stream)
                .toArray(String[]::new);
        resourceBundlesMessageSource.addBasenames(basenames);

        final Stream<MessageSource> programmaticMessageSources = Stream.of(
                Optional.ofNullable(facetValuePropertiesConfiguration.getInternalMessageSourceBeanNames()),
                Optional.ofNullable(facetValuePropertiesConfiguration.getMessageSourceBeanNames()))
                .flatMap(Optional::stream)
                .flatMap(List::stream)
                .map(beanName -> ctx.getBean(beanName, MessageSource.class));

        final List<MessageSource> messageSources
                = Streams.concat(Stream.of(
                        resourceBundlesMessageSource),
                        programmaticMessageSources)
                        .toList();

        return new FacetValueService(messageSources, facetValuePropertiesConfiguration.getFacetValuePropertyFormat());
    }

}
