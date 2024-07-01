package com.github.stazxr.muses.base.core.config;

import com.github.stazxr.muses.base.core.properties.MusesHeaderProperties;
import com.github.stazxr.muses.base.core.util.MusesEnvironmentUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for MusesHeader properties.
 * <p>
 * This configuration class is used to inject the MusesHeaderProperties object into MusesEnvironmentUtil.
 * </p>
 *
 * @since 2024-05-05
 * @author SunTao
 */
@Configuration
@EnableConfigurationProperties({MusesHeaderProperties.class})
public class MusesHeaderPropertiesConfig {
    /**
     * Constructor used to inject the MusesHeaderProperties object.
     *
     * @param properties MusesHeaderProperties object
     */
    public MusesHeaderPropertiesConfig(MusesHeaderProperties properties) {
        MusesEnvironmentUtil.setHeaderProperties(properties);
    }
}
