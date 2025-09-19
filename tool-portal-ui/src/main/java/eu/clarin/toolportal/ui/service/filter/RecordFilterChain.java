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
package eu.clarin.toolportal.ui.service.filter;

import eu.clarin.cmdi.vlo.openapi.client.model.VloRecord;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author twagoo
 */
public class RecordFilterChain implements RecordFilter {

    private final List<RecordFilter> filters;

    public RecordFilterChain() {
        filters = new CopyOnWriteArrayList<>();
    }

    public void addFilters(Collection<RecordFilter> filters) {
        filters.addAll(filters);
    }

    public void addFilter(RecordFilter filter) {
        filters.add(filter);
    }

    @Override
    public VloRecord apply(VloRecord record) {
        VloRecord result = record;
        for (RecordFilter filter : filters) {
            result = filter.apply(result);
        }
        return result;
    }

}
