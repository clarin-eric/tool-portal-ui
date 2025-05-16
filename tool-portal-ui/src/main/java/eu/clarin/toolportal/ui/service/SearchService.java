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

import eu.clarin.cmdi.vlo.openapi.client.api.FacetsApi;
import eu.clarin.cmdi.vlo.openapi.client.api.RecordsApi;
import eu.clarin.cmdi.vlo.openapi.client.model.VloRecordSearchResult;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author twagoo
 */
@Service
public class SearchService {

    private final RecordsApi records;
    private final FacetsApi facets;

    public SearchService(RecordsApi records, FacetsApi facets) {
        this.records = records;
        this.facets = facets;
    }

    public VloRecordSearchResult search(String query, List<String> filters, Integer from, Integer size) {
        return records.getRecords(query, filters, from, size);
    }

}
