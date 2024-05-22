package com.github.stazxr.muses.utils.base.collection;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类，提供了一些常用的集合操作方法。
 *
 * @author SunTao
 * @since 2022-01-15
 */
public class CollectionUtils {
    /**
     * 计算 HashMap 的初始容量。
     *
     * @param expectedSize 期望的容量
     * @return HashMap 的初始容量
     */
    public static int mapSize(int expectedSize) {
        return (int) ((float) expectedSize / 0.75f + 1.0f);
    }

    /**
     * 判断集合是否为空。
     *
     * @param collection 集合对象
     * @return 如果集合为 {@code null} 或者为空则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * 判断字典是否为空。
     *
     * @param map 字典对象
     * @return 如果字典为 {@code null} 或者为空则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }
}
