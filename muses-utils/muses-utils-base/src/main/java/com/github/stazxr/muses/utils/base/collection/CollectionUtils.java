package com.github.stazxr.muses.utils.base.collection;

import java.util.Collection;
import java.util.Map;

/**
 * Collection utility class providing commonly used operations on collections.
 *
 * This class includes methods for calculating initial capacity of HashMaps,
 * checking if collections or maps are empty.
 *
 * @author SunTao
 * @since 2022-01-15
 */
public class CollectionUtils {
    /**
     * Calculates the initial capacity of a HashMap.
     *
     * @param expectedSize the expected size
     * @return the initial capacity of the HashMap
     */
    public static int mapSize(int expectedSize) {
        return (int) ((float) expectedSize / 0.75f + 1.0f);
    }

    /**
     * Checks if a collection is empty.
     *
     * @param collection the collection to check
     * @return {@code true} if the collection is {@code null} or empty, otherwise {@code false}
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Checks if a map is empty.
     *
     * @param map the map to check
     * @return {@code true} if the map is {@code null} or empty, otherwise {@code false}
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }
}
