package com.github.stazxr.muses.base.core.context;

import com.github.stazxr.muses.base.core.entity.MusesContextTag;
import com.github.stazxr.muses.base.core.exception.context.MusesContextErrorCode;
import com.github.stazxr.muses.base.core.exception.context.MusesContextException;
import com.github.stazxr.muses.base.core.properties.MusesContextProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Core context management for Muses framework.
 *
 * This class manages context tags and their values within a ConcurrentHashMap.
 * It provides methods to retrieve and store context tags based on configuration properties.
 *
 * @author SunTao
 * @since 2024-07-02
 */
@Slf4j
public class MusesCoreContext {
    private static final Map<String, String> CONTEXT_MAP = new ConcurrentHashMap<>();

    private static final Map<String, Boolean> CACHE_MAP = new ConcurrentHashMap<>();

    private final MusesContextProperties musesContextProperties;

    public MusesCoreContext(MusesContextProperties musesContextProperties) {
        if (musesContextProperties == null) {
            throw new MusesContextException(MusesContextErrorCode.MUS_CXT_002);
        }

        this.musesContextProperties = musesContextProperties;
    }

    /**
     * Retrieves the value associated with the given key from the context map.
     *
     * @param key The key to retrieve the value for
     * @return The value associated with the key, or null if the key is not found
     */
    public String get(String key) {
        return CONTEXT_MAP.get(key);
    }

    /**
     * Stores the value of the given context tag in the context map if it is configured to be cached.
     *
     * @param musesContextTag The context tag to store
     */
    public void put(MusesContextTag musesContextTag) {
        // Check if the tag is configured to be cached
        String tagName = musesContextTag.getTagName();
        if (CACHE_MAP.containsKey(tagName)) {
            if (CACHE_MAP.get(tagName)) {
                CONTEXT_MAP.put(tagName, musesContextTag.getTagValue());
            }
        } else {
            // Initialize cache status for the tag
            List<String> musesTags = musesContextProperties.getTagNames();
            boolean shouldCache = musesTags.contains(tagName);
            CACHE_MAP.put(tagName, shouldCache);
            if (shouldCache) {
                // Store the tag value if it is configured to be cached
                CONTEXT_MAP.put(tagName, musesContextTag.getTagValue());
            }
        }
    }

    /**
     * Stores the values of all provided context tags in the context map.
     *
     * @param musesContextTags The list of context tags to store
     */
    public void putAll(List<MusesContextTag> musesContextTags) {
        musesContextTags.forEach(this::put);
    }
}
