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

import eu.clarin.cmdi.vlo.openapi.client.model.VloRecordSearchResult;
import eu.clarin.toolportal.ui.service.SearchService;
import static eu.clarin.toolportal.ui.web.HtmxUtils.isHtmxRequest;
import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author twagoo
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {

    private final SearchService service;

    public SearchController(SearchService searchService) {
        this.service = searchService;
    }

    @GetMapping
    public String index(Model model,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestHeader Map<String, String> headers) {
        final VloRecordSearchResult search
                = service.search(query, Collections.emptyList(), from, size);
        model.addAttribute("result", search);
        model.addAttribute("query", query);
        model.addAttribute("resultsPerPage", size);
        model.addAttribute("pageCount", Math.ceilDiv(search.getNumFound(), size));
        model.addAttribute("currentPage", 1 + (from / size));

        if (isHtmxRequest(headers)) {
            //return search results
            return "search/search :: #search-results-and-facets";
        } else {
            //return entire page
            return "search/search";
        }
    }
}
