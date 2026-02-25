/*
 * Copyright (C) 2024 CLARIN ERIC
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
package eu.clarin.toolportal.ui.configuration;

import com.google.common.collect.ImmutableSet;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Configuration for markup templates and message properties files
 *
 * @author twagoo
 */
@Configuration
public class TemplateConfiguration {

    //TODO: make this configurable
    private final String externalImportDir = null;
    private final String fallbackImportClassPathDir = "/templates/import";

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        //TODO: read in extra sources from external configuration
        messageSource.setBasenames(
                "classpath:messages/common",
                "classpath:messages/fields",
                "classpath:messages/search",
                "classpath:messages/records",
                // git.properties is generated at build time
                // by git-commit-id-maven-plugin
                "classpath:/git");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(60);
        return messageSource;
    }

    @Bean
    public SpringTemplateEngine templateEnginge(MessageSource messageSource, ITemplateResolver templateResolver) {
        final SpringTemplateEngine engine = new SpringTemplateEngine();

        final ITemplateResolver importResolver;
        if (externalImportDir != null) {
            importResolver = injectableSnippetsTemplateResolver();
        } else {
            importResolver = injectableSnippetsFallbackTemplateResolver();
        }

        engine.setTemplateResolvers(ImmutableSet.of(templateResolver, importResolver));
        engine.setMessageSource(messageSource);

        return engine;
    }

    private ITemplateResolver injectableSnippetsTemplateResolver() {
        final FileTemplateResolver resolver = new FileTemplateResolver();

        resolver.setPrefix(externalImportDir + "/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        resolver.setOrder(null);

        return resolver;
    }

    private ITemplateResolver injectableSnippetsFallbackTemplateResolver() {
        final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver(getClass().getClassLoader());

        resolver.setPrefix(fallbackImportClassPathDir + "/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        resolver.setOrder(null);

        return resolver;
    }
}
