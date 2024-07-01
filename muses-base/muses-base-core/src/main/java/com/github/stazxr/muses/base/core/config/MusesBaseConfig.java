package com.github.stazxr.muses.base.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Muses base module.
 * <p>
 * This configuration class enables component scanning for the Muses base module
 * to automatically detect and register Spring components like controllers, services,
 * repositories, and other beans within the 'com.github.stazxr.muses.base' package and its sub-packages.
 * </p>
 *
 * @since 2024-05-16
 * @author SunTao
 */
@Configuration
@ComponentScan("com.github.stazxr.muses.base")
public class MusesBaseConfig {
}
