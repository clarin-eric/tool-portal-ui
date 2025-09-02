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
import eu.clarin.toolportal.ui.service.RecordsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    public RecordsController(RecordsService service) {
        this.service = service;
    }

    @GetMapping("/{recordId}")
    public String record(Model model,
            @PathVariable String recordId,
            @RequestParam(name = "backLink", defaultValue = "false") Boolean includeBackLink) {
        final VloRecord record = service.getRecordById(recordId);
        model.addAttribute("record", record);
        model.addAttribute("includeBackLink", includeBackLink);
        
        //TODO: if set, add header value for HTMX origin URL to model for back link

        return "/records/record";
    }

}
