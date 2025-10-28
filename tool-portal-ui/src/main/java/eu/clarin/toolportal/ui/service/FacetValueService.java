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

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

/**
 * Provides labels for facet values
 *
 * @author twagoo
 */
public class FacetValueService {

    public static final String DEFAULT_CODE_FORMAT = "facetValues.%s.%s";

    private final String codeFormat;
    private final List<MessageSource> messageSources;
    
    /**
     * Use the provided message sources and the default code format (prefix facetValues.{facet}.{value})
     *
     * @param messageSources
     */
    public FacetValueService(List<MessageSource> messageSources) {
        this(messageSources, DEFAULT_CODE_FORMAT);
    }

    /**
     * Use the provided message sources and code format
     * @param messageSources
     * @param codeFormat a String with two %s placeholders for facet name and facet value
     */
    public FacetValueService(List<MessageSource> messageSources, String codeFormat) {
        this.messageSources = messageSources;
        this.codeFormat = codeFormat;
    }

    public String getLabel(String facet, String value, Locale locale) {
        final String code = String.format(codeFormat, facet, value);
        return messageSources.stream()
                .flatMap(ms -> {
                    try {
                        return Stream.of(ms.getMessage(code, null, locale));
                    } catch (NoSuchMessageException ex) {
                        //no message found for code in the message source
                        return Stream.empty();
                    }
                })
                //terminate on first message
                .findFirst()
                //or else original value
                .orElse(value);
    }

}
