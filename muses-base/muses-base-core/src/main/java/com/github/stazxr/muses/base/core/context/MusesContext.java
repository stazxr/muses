package com.github.stazxr.muses.base.core.context;

import com.github.stazxr.muses.base.core.entity.MusesContextTag;

import java.util.List;

/**
 * Utility class for interacting with MusesCoreContext.
 * Provides static methods to get and put context tags.
 *
 * @author SunTao
 * @since 2024-07-02
 */
public class MusesContext {
    /**
     * Retrieves the value associated with the specified key from the context.
     *
     * @param key The key to retrieve from the context
     * @return The value associated with the key, or null if not found
     */
    public static String get(String key) {
        return getContext().get(key);
    }

    /**
     * Adds a single context tag to the context.
     *
     * @param tag The context tag to add
     */
    public static void put(MusesContextTag tag) {
        getContext().put(tag);
    }

    /**
     * Adds multiple context tags to the context.
     *
     * @param tags The list of context tags to add
     */
    public static void putAll(List<MusesContextTag> tags) {
        getContext().putAll(tags);
    }

    /**
     * Retrieves the current MusesCoreContext instance from the thread-local context holder.
     *
     * @return The current MusesCoreContext instance
     */
    private static MusesCoreContext getContext() {
        return MusesCoreContextHolder.get();
    }
}
