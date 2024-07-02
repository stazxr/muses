package com.github.stazxr.muses.base.core.config;

import com.github.stazxr.muses.base.core.filter.MusesContextFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Configuration class for registering MusesContextFilter as a servlet filter.
 *
 * @since 2024-05-25
 * @author SunTao
 */
@Configuration
public class MusesFilterConfig {
    private final MusesContextFilter musesContextFilter;

    /**
     * Constructor with dependency injection for MusesContextFilter.
     *
     * @param musesContextFilter The MusesContextFilter to be used
     */
    @Autowired
    public MusesFilterConfig(MusesContextFilter musesContextFilter) {
        this.musesContextFilter = musesContextFilter;
    }

    /**
     * Configures and registers MusesContextFilter as a servlet filter.
     *
     * @return The FilterRegistrationBean for MusesContextFilter
     */
    @Bean
    public FilterRegistrationBean<MusesContextFilter> loggingFilter() {
        FilterRegistrationBean<MusesContextFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(musesContextFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
