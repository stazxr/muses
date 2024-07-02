package com.github.stazxr.muses.utils.log.constants;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Constants for SLF4J markers used in logging.
 * Defines markers for monitoring and auditing logs.
 *
 * Marker is used to categorize logs for specific purposes.
 *
 * @author SunTao
 * @since 2024-05-12
 */
public class MarkerConstants {
    /**
     * Marker for monitoring logs.
     * Used to indicate logs related to system monitoring.
     */
    public static final Marker MONITOR_MARKER = MarkerFactory.getMarker("MONITOR");

    /**
     * Marker for audit logs.
     * Used to indicate logs related to auditing actions.
     */
    public static final Marker AUDIT_MARKER = MarkerFactory.getMarker("AUDIT");
}
