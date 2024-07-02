package com.github.stazxr.muses.base.core.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.stazxr.muses.base.core.constants.MusesBasePropertiesPrefix;
import com.github.stazxr.muses.base.core.entity.MusesContextTag;
import com.github.stazxr.muses.base.core.exception.context.MusesContextErrorCode;
import com.github.stazxr.muses.base.core.exception.context.MusesContextException;
import com.github.stazxr.muses.utils.log.constants.MarkerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Muses context properties.
 *
 * This class reads properties under the 'muses.context' prefix from the configuration file and maps them to corresponding fields.
 *
 * @since 2024-05-05
 * @author SunTao
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = MusesBasePropertiesPrefix.MUSES_CONTEXT_PREFIX)
public class MusesContextProperties implements Serializable, InitializingBean {
    private static final long serialVersionUID = 1761392456056935111L;

    private static final String DEFAULT_MUSES_TAGS_FILE = "/muses-default-tags.yml";

    /**
     * List of Muses context tags.
     */
    private List<MusesContextTag> tags;

    public void setTags(List<MusesContextTag> tags) {
        log.debug(MarkerConstants.MONITOR_MARKER, "Loading customizing Muses context tags: " + tags);
        this.tags = tags;
    }

    public List<MusesContextTag> getTags() {
        return tags;
    }

    public List<String> getTagNames() {
        return this.tags.stream().map(MusesContextTag::getTagName).collect(Collectors.toList());
    }

    /**
     * Initializes the bean after properties are set.
     */
    @Override
    public void afterPropertiesSet() {
        log.info(MarkerConstants.MONITOR_MARKER, "Loading default Muses context tags");
        try (InputStream is = this.getClass().getResourceAsStream(DEFAULT_MUSES_TAGS_FILE)) {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            MusesContextProperties defaultContextProperties = objectMapper.readValue(is, MusesContextProperties.class);
            addMusesTags(defaultContextProperties.getTags());
        } catch (Exception e) {
            throw new MusesContextException(MusesContextErrorCode.MUS_CXT_001, e);
        } finally {
            log.debug(MarkerConstants.MONITOR_MARKER, "Loaded all Muses context tags: " + tags);
        }
    }

    /**
     * Adds Muses context tags, merging with existing tags.
     *
     * @param tags The list of tags to add.
     */
    private void addMusesTags(List<MusesContextTag> tags) {
        if (this.tags == null) {
            this.tags = new ArrayList<>(tags);
        } else {
            List<MusesContextTag> mergeTags = new ArrayList<>(tags);
            for (MusesContextTag customTag : this.tags) {
                if (mergeTags.contains(customTag)) {
                    // Default tags cannot be customized
                    log.warn(MarkerConstants.MONITOR_MARKER, "Tag '{}' cannot be customized", customTag.getTagName());
                } else {
                    mergeTags.add(customTag);
                }
            }
            // Assign the merged tags back to 'tags'
            this.tags = new ArrayList<>(mergeTags);
        }
    }
}
