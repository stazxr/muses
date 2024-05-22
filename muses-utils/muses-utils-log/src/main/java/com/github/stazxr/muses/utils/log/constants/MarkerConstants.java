package com.github.stazxr.muses.utils.log.constants;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * 日志标识
 *
 * @author SunTao
 * @since 2024-05-12
 */
public class MarkerConstants {
    /**
     * 监控日志标识
     */
    public static final Marker MONITOR_MARKER = MarkerFactory.getMarker("MONITOR");

    /**
     * 审计日志标识
     */
    public static final Marker AUDIT_MARKER = MarkerFactory.getMarker("AUDIT");
}
