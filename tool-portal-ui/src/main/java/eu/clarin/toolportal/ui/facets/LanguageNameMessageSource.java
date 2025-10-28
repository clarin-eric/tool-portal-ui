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

import com.google.common.collect.ImmutableMap;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.springframework.context.support.AbstractMessageSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Produces a language name for a language code value (which may be of type
 * code:xyz or name:....)
 *
 * @author twagoo
 */
public class LanguageNameMessageSource extends AbstractMessageSource {

    public static final Pattern CODE_PATTERN = Pattern.compile("^code:(?<languageCode>.+)$");
    public static final Pattern NAME_PATTERN = Pattern.compile("^(name:?)(?<languageName>.+)$");
    public static final String CODENAME_MAP_RESOURCE = "/maps/iso-639-3.xml";
    public static final String LANGUAGE_CODE_VOCAB_ITEM_XPATH = "//Element/ValueScheme/Vocabulary/enumeration/item";

    private final String codePrefix;

    private Map<String, String> codeNameMap;

    public LanguageNameMessageSource(String codePrefix) {
        this.codePrefix = codePrefix + ".";
        codeNameMap = Collections.emptyMap();
    }

    @PostConstruct
    public final void init() throws IOException {
        try {
            // Load the code -> language name map
            this.codeNameMap = loadCodeNameMap();
        } catch (SAXException | ParserConfigurationException | XPathExpressionException ex) {
            throw new RuntimeException("Exception occurred while try to read the language codes map", ex);
        }
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        //TODO: user Matcher to split
        if (code.startsWith(codePrefix)) {
            return valueToName(code.substring(codePrefix.length()), locale);
        } else {
            return null;
        }
    }

    /**
     * Resolve the best possible name for the "language code" key
     *
     * @param key key without prefix
     * @param locale locale
     * @return
     */
    private String valueToName(String key, Locale locale) {
        final Matcher codeMatcher = CODE_PATTERN.matcher(key);
        if (codeMatcher.matches()) {
            //code match, convert to name
            return getLanguageNameForCode(codeMatcher.group("languageCode"), locale);
        } else {
            //not a code, extract the name
            final Matcher nameMatcher = NAME_PATTERN.matcher(key);
            if (nameMatcher.matches()) {
                return nameMatcher.group("languageName");
            }
        }
        //fallback: return original value
        return key;
    }

    private String getLanguageNameForCode(String languageCode, Locale locale) {
        return codeNameMap.getOrDefault(languageCode, languageCode);
    }

    /**
     * Loads the code -> language name map from the bundled CMDI XML
     *
     * @return a map with ISO 639-3 language code as key and language name in
     * English as value
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws XPathExpressionException
     */
    public static Map<String, String> loadCodeNameMap() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        final DocumentBuilder builder = factory.newDocumentBuilder();

        final XPath xpath = XPathFactory.newInstance().newXPath();
        final XPathExpression xpathExpression = xpath.compile(LANGUAGE_CODE_VOCAB_ITEM_XPATH);

        try (InputStream is = LanguageNameMessageSource.class.getResourceAsStream(CODENAME_MAP_RESOURCE)) {
            final Document doc = builder.parse(is);
            final Object result = xpathExpression.evaluate(doc, XPathConstants.NODESET);
            final NodeList nodes = (NodeList) result;

            final ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();
            for (int i = 0; i < nodes.getLength(); i++) {
                final Node item = nodes.item(i);
                final Node appInfo = item.getAttributes().getNamedItem("AppInfo");
                mapBuilder.put(item.getTextContent(), sanitizeLanguageName(appInfo.getTextContent()));
            }

            return mapBuilder.build();
        }
    }

    private static String sanitizeLanguageName(String value) {
        final Pattern LANGUAGE_NAME_ = Pattern.compile("^(?<name>.*) \\([a-z][a-z][a-z]\\)$");
        final Matcher matcher = LANGUAGE_NAME_.matcher(value);
        if (matcher.matches()) {
            return matcher.group("name");
        } else {
            return value;
        }
    }

    /**
     * Not used, see documentation of {@link AbstractMessageSource#resolveCode(java.lang.String, java.util.Locale)
     * }
     *
     * @param code
     * @param locale
     * @return always null
     */
    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        //returning null here since we don't need to process parameters. 
        return null;
    }

}
