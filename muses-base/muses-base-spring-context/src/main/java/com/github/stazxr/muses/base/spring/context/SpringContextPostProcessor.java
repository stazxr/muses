package com.github.stazxr.muses.base.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

/**
 * SpringContext 工具类
 *
 * @author SunTao
 * @since 2024-04-30
 */
public class SpringContextPostProcessor implements BeanFactoryPostProcessor, ApplicationContextAware, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // TIPS 用于在 Spring 容器实例化 Bean 之前对 BeanFactory 进行后置处理
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.setApplicationContext(applicationContext);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
