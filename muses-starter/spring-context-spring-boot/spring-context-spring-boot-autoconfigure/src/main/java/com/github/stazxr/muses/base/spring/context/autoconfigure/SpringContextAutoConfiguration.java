package com.github.stazxr.muses.base.spring.context.autoconfigure;

import com.github.stazxr.muses.base.spring.context.SpringContextPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * SpringContext 自动配置
 *
 * @author SunTao
 * @since 2024-05-01
 */
@Configuration
@Import(SpringContextPostProcessor.class)
public class SpringContextAutoConfiguration {
}
