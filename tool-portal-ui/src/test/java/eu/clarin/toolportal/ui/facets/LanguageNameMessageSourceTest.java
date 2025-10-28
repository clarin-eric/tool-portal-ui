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
package eu.clarin.toolportal.ui.facets;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author twagoo
 */
public class LanguageNameMessageSourceTest {

    private LanguageNameMessageSource instance;

    @BeforeEach
    public void setUp() {
        instance = new LanguageNameMessageSource("prefix");
    }

    /**
     * Test of resolveCodeWithoutArguments method, of class
     * LanguageNameMessageSource.
     */
    @Test
    public void testResolveCode() throws IOException {
        assertNull(instance.resolveCode("prefix.code:eng", Locale.ROOT), "Should always return null");
        assertNull(instance.resolveCode(null, null), "Should always return null");
    }

    /**
     * Test of resolveCodeWithoutArguments method, of class
     * LanguageNameMessageSource.
     */
    @Test
    public void testResolveCodeWithoutArguments() throws IOException {
        instance.init();
        assertEquals("French", instance.resolveCodeWithoutArguments("prefix.code:fra", Locale.ROOT));
        assertEquals("Named language", instance.resolveCodeWithoutArguments("prefix.name:Named language", Locale.ROOT));
        assertEquals("Badly coded", instance.resolveCodeWithoutArguments("prefix.Badly coded", Locale.ROOT));
    }

    /**
     * Test of loadCodeNameMap method, of class LanguageNameMessageSource.
     */
    @Test
    public void testLoadCodeNameMap() throws Exception {
        final Map<String, String> codeMap = LanguageNameMessageSource.loadCodeNameMap();
        assertNotNull(codeMap);
        assertFalse(codeMap.isEmpty());
        assertEquals(7679, codeMap.size());
        assertEquals("Ghotuo", codeMap.get("aaa"));
        assertEquals("English", codeMap.get("eng"));
        assertEquals("Zuojiang Zhuang", codeMap.get("zzj"));
    }

}
