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

import com.google.common.collect.Lists;
import eu.clarin.cmdi.vlo.openapi.client.model.VloRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author twagoo
 */
public abstract class RecordFieldContentFilter implements RecordFilter {

    private final String field;

    public RecordFieldContentFilter(String field) {
        this.field = field;
    }

    @Override
    public VloRecord apply(VloRecord record) {
        final Map<String, List<Object>> fields = record.getFields();
        if (fields != null) {
            final List<Object> values = fields.get(field);
            if (values != null) {
                final ArrayList<Object> newValues = Lists.newArrayListWithExpectedSize(values.size());
                for (Object value : values) {
                    if (value instanceof String) {
                        newValues.add(filterValue((String) value));
                    } else {
                        newValues.add(value);
                    }
                }
                fields.replace(field, newValues);
            }
        }
        return record;
    }

    protected abstract String filterValue(String string);

}
