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
package eu.clarin.toolportal.ui.web;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author twagoo
 */
public class HtmxUtils {

    //request headers
    public static final String HX_REQUEST_HEADER = "hx-request";
    public static final String HX_TARGET_REQUEST_HEADER = "hx-target";

    //response headers
    public static final String HX_PUSH_URL_RESPONSE_HEADER = "HX-Push-Url";

    /**
     * Determines whether the request headers indicate an request triggered via
     * HTMX
     *
     * @param headers headers of the request to evaluate
     * @return true if and only if the HX-Request header was found
     */
    public static boolean isHtmxRequest(Map<String, String> headers) {
        return headers.containsKey(HX_REQUEST_HEADER);
    }

    /**
     * Determines whether the specified target is the target of the HTMX
     * request
     * 
     * @param headers
     * @param target
     * @return false iff the target does not match, or the request is not an HTMX request
     */
    public static boolean isHtmxTarget(Map<String, String> headers, String target) {
        return Objects.equals(target, headers.get(HX_TARGET_REQUEST_HEADER));
    }

    public static void pushUrl(HttpServletResponse response, String url) {
        response.addHeader(HX_PUSH_URL_RESPONSE_HEADER, url);
    }

}
