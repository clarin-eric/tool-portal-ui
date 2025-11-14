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

import com.google.common.collect.ImmutableList;
import eu.clarin.cmdi.vlo.openapi.client.model.Facet;
import eu.clarin.cmdi.vlo.openapi.client.model.VloRecord;
import eu.clarin.toolportal.ui.configuration.SearchConfigurationProperties;
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
    private final SearchConfigurationProperties searchConfig;

    public HomeController(RecordsService recordsService, RecordFilter recordFilter, FacetsService facetsService, SearchConfigurationProperties searchConfig) {
        this.recordsService = recordsService;
        this.recordFilter = recordFilter;
        this.facetsService = facetsService;
        this.searchConfig = searchConfig;
    }

    //TODO: make configurable
    public final List<String> homepageFacets = ImmutableList.of("fieldOfStudy", "toolType", "languageCode", "keywords");

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(Model model) {
        //TODO: make configurable
        model.addAttribute("highlightedIds", List.of(
                "CSD_32_Tools_47_WebCelex.cmdi.xml",
                "https_58__47__47_tool-portal.clarin.eu_47_metadata_47_codemeta_47_galahad-train-battery_1.1.0.json",
                "https_58__47__47_tool-portal.clarin.eu_47_metadata_47_switchboard_47_ELAN.json"));

        //TODO: make configurable
        model.addAttribute("facets", homepageFacets);

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

        final List<Facet> facets = facetsService.getFacets(null, Collections.emptyList(), homepageFacets, 12);

        model.addAttribute("facets", facets);

        return new ModelAndView("home/facets :: facets");
    }
}
