package com.lmt.selfblog.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * Configures Spring's {@link MessageSource} for i18n error messages.
 *
 * <p>Message files are resolved from {@code src/main/resources/i18n/}:
 * <ul>
 *   <li>{@code messages.properties}    — default (English)</li>
 *   <li>{@code messages_vi.properties} — Vietnamese</li>
 * </ul>
 *
 * <p>The active locale is determined by the {@code Accept-Language} HTTP header
 * sent by the client. Supported locales: {@code en}, {@code vi}.
 * Falls back to English ({@link Locale#ENGLISH}) when the header is absent or
 * the requested locale is not supported.
 */
@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:i18n/messages");
        source.setDefaultEncoding("UTF-8");
        // Cache messages for 1 hour in production; set to 0 for dev hot-reload
        source.setCacheSeconds(3600);
        source.setUseCodeAsDefaultMessage(false);
        return source;
    }

    /**
     * Resolves the locale from the {@code Accept-Language} request header.
     * Restricts to supported locales and defaults to English.
     */
    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(List.of(Locale.ENGLISH, Locale.of("vi")));
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }
}
