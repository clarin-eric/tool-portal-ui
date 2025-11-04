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

import eu.clarin.cmdi.vlo.openapi.client.model.VloRecord;
import eu.clarin.toolportal.ui.service.filter.RecordFilter;
import eu.clarin.toolportal.ui.service.RecordsService;
import eu.clarin.toolportal.ui.web.HtmxUtils;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String record(Model model,
            @PathVariable String recordId,
            @RequestParam(name = "backLink", defaultValue = "false") Boolean includeBackLink) {
        final VloRecord record = RecordsService.applyFilter(
                service.getRecordById(recordId),
                recordFilter);
        model.addAttribute("record", record);
        model.addAttribute("includeBackLink", includeBackLink);

        return "records/record";
    }

    @GetMapping("/{recordId}/metadata")
    public String allMetadata(Model model,
            @RequestHeader Map<String, String> headers,
            @PathVariable String recordId,
            @RequestParam(name = "backLink", defaultValue = "false") Boolean includeBackLink) {
        String xml = service.getCmdiXml(recordId);
        model.addAttribute("xml", xml);
        if (HtmxUtils.isHtmxRequest(headers)) {
            // serve only fragment
            return "records/allMetadata :: allMetadata";
        } else {
            return record(model, recordId, includeBackLink);
        }
    }

}
