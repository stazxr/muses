package com.github.stazxr.muses.base.core.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a context tag in the Muses framework.
 *
 * This class encapsulates properties related to a context tag:
 * - tagName: Name of the tag.
 * - tagValue: Value associated with the tag.
 *
 * @author SunTao
 * @since 2024-05-22
 */
public class MusesContextTag implements Serializable {
    private static final long serialVersionUID = -5644282630954166219L;

    /**
     * Name of the tag.
     */
    private String tagName;

    /**
     * Value associated with the tag.
     */
    private String tagValue;

    public MusesContextTag() {
    }

    /**
     * Constructs a MusesContextTag with tagName and tagValue.
     *
     * @param tagName  The name of the tag.
     * @param tagValue The value associated with the tag.
     */
    public MusesContextTag(String tagName, String tagValue) {
        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MusesContextTag that = (MusesContextTag) o;
        return Objects.equals(tagName, that.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName);
    }

    @Override
    public String toString() {
        return "MusesContextTag{" +
                "tagName='" + tagName + '\'' +
                ", tagValue='" + tagValue + '\'' +
                '}';
    }
}
