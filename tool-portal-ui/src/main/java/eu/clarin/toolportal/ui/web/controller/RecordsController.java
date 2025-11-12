/*
 * Copyright (C) 2025 CLARIN ERIC
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
import eu.clarin.cmdi.vlo.openapi.client.model.VloRecord;
import eu.clarin.toolportal.ui.service.filter.RecordFilter;
import eu.clarin.toolportal.ui.service.RecordsService;
import eu.clarin.toolportal.ui.web.HtmxUtils;
import static eu.clarin.toolportal.ui.web.controller.SearchController.DEFAULT_QUERY;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author twagoo
 */
@Controller
@RequestMapping(value = "/records")
public class RecordsController {

    private final RecordsService service;

    private final RecordFilter recordFilter;

    public RecordsController(RecordsService service, RecordFilter recordFilter) {
        this.service = service;
        this.recordFilter = recordFilter;
    }

    @GetMapping("/{recordId}")
    public List<ModelAndView> record(Model model,
            @RequestHeader Map<String, String> headers,
            @PathVariable String recordId,
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "fq", required = false) List<String> filterQuery,
            @RequestParam(name = "tab", defaultValue = "overview") String tab) {
        final VloRecord record = RecordsService.applyFilter(
                service.getRecordById(recordId),
                recordFilter);
        model.addAttribute("record", record);
        model.addAttribute("query", query);
        model.addAttribute("searchFilters", filterQuery);

        if (HtmxUtils.isHtmxTarget(headers, "recordTabsContent")) {
            // serve only fragment
            model.addAttribute("recordId", recordId);
            model.addAttribute("tab", tab);
            return ImmutableList.of(
                    //overview tab content
                    new ModelAndView("records/overview :: overview"),
                    //main contents nav bar with updated state
                    new ModelAndView("records/contentTabs :: mainContentTabsNav"));
        } else {
            // serve full page with specified tab selected
            model.addAttribute("tab", tab);
            return ImmutableList.of(new ModelAndView("records/record"));
        }
    }

    @GetMapping("/{recordId}/metadata")
    public List<ModelAndView> allMetadata(Model model,
            @RequestHeader Map<String, String> headers,
            @PathVariable String recordId,
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "fq", required = false) List<String> filterQuery) {
        String xml = service.getCmdiXml(recordId);
        model.addAttribute("xml", xml);
        if (HtmxUtils.isHtmxRequest(headers)) {
            // serve only fragment
            model.addAttribute("recordId", recordId);
            model.addAttribute("tab", "metadata");
            return ImmutableList.of(
                    //all metadata fragment
                    new ModelAndView("records/allMetadata :: allMetadata"),
                    //main contents nav bar with updated state
                    new ModelAndView("records/contentTabs :: mainContentTabsNav"));
        } else {
            // serve full page with "all metadata" tab selected
            return record(model, headers, recordId, query, filterQuery, "metadata");
        }
    }

}
