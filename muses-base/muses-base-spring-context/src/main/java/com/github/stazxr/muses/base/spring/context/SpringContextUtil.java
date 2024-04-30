package com.github.stazxr.muses.base.spring.context;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * SpringContext 工具类
 *
 * @author SunTao
 * @since 2024-04-30
 */
public class SpringContextUtil {
    /**
     * IOC
     */
    private static ApplicationContext applicationContext;

    /**
     * 设置 ApplicationContext 信息
     *
     * @param applicationContext 应用上下文信息
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 获取 ApplicationContext 对象
     *
     * @return ApplicationContext
     */
    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据名称获取 Bean
     *
     * @param name beanName
     * @return Object
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过类信息获取 Bean
     *
     * @param clazz class
     * @param <T> Type
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过名称和类信息获取 Bean
     *
     * @param name beanName
     * @param clazz class
     * @param <T> Type
     * @return T
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 通过上下文环境信息
     *
     * @return Environment
     */
    public static Environment getEnvironment() {
        return getApplicationContext().getEnvironment();
    }

    /**
     * 获取环境变量
     *
     * @param key 环境变量名称
     * @return 环境变量
     */
    public static String getProperty(String key) {
        return getEnvironment().getProperty(key);
    }

    /**
     * 获取环境变量
     *
     * @param key 环境变量名称
     * @param defaultValue 当环境变量不存在时，默认值
     * @return 环境变量
     */
    public static String getProperty(String key, String defaultValue) {
        return getEnvironment().getProperty(key, defaultValue);
    }

    /**
     * 获取环境变量
     *
     * @param key 环境变量名称
     * @param clazz 环境变量类型
     * @return 环境变量 T
     */
    public static <T> T getProperty(String key, Class<T> clazz) {
        return getEnvironment().getProperty(key, clazz);
    }

    /**
     * 获取环境变量
     *
     * @param key 环境变量名称
     * @param clazz 环境变量类型
     * @param defaultValue 当环境变量不存在时，默认值
     * @return 环境变量 T
     */
    public static <T> T getProperty(String key, Class<T> clazz, T defaultValue) {
        return getEnvironment().getProperty(key, clazz, defaultValue);
    }

    /**
     * 获取当前激活的环境
     *
     * @return 当前环境名称
     */
    public static String getActiveProfile() {
        return getEnvironment().getActiveProfiles()[0];
    }
}
