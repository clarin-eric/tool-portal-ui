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
package eu.clarin.toolportal.ui.web.controller;

import eu.clarin.cmdi.vlo.openapi.client.model.Facet;
import eu.clarin.cmdi.vlo.openapi.client.model.VloRecord;
import eu.clarin.toolportal.ui.configuration.HomeConfigurationProperties;
import eu.clarin.toolportal.ui.service.FacetsService;
import eu.clarin.toolportal.ui.service.RecordsService;
import eu.clarin.toolportal.ui.service.filter.RecordFilter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author twagoo
 */
@Controller
@RequestMapping(value = "/")
public class HomeController {

    
    private final RecordsService recordsService;
    private final RecordFilter recordFilter;
    private final FacetsService facetsService;
    
    private final HomeConfigurationProperties config;

    public HomeController(RecordsService recordsService, RecordFilter recordFilter, FacetsService facetsService, HomeConfigurationProperties searchConfig) {
        this.recordsService = recordsService;
        this.recordFilter = recordFilter;
        this.facetsService = facetsService;
        this.config = searchConfig;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(Model model) {
        model.addAttribute("highlightedIds", config.getHighlightedItemIds());
        model.addAttribute("facets", config.getFacets());

        return new ModelAndView("home/home");
    }

    @GetMapping(path = "/home/highlighted/{recordId}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView facets(@PathVariable String recordId, Model model) {
        final VloRecord record = RecordsService.applyFilter(
                recordsService.getRecordById(recordId),
                recordFilter);

        final Map<String, List<Object>> fields = record.getFields();

        model.addAttribute("id", recordId);
        if (fields != null) {
            //TODO: extract title from list
            model.addAttribute("title", fields.get("name"));
            //TODO: truncate description
            model.addAttribute("description", fields.get("description"));
        }

        return new ModelAndView("home/highlighted :: highlight");
    }

    @GetMapping(path = "/home/facets", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView facets(Model model) {

        final List<Facet> facets = facetsService.getFacets(null, Collections.emptyList(), config.getFacets(), config.getFacetValueCount());

        model.addAttribute("facets", facets);

        return new ModelAndView("home/facets :: facets");
    }
}
