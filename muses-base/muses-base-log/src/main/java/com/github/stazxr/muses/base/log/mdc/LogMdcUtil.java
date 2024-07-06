package com.github.stazxr.muses.base.log.mdc;

import com.github.stazxr.muses.base.core.util.MusesEnvironmentUtil;
import org.slf4j.MDC;

/**
 * Utility class for managing Mapped Diagnostic Context (MDC) in logging.
 * MDC allows storing contextual information related to log messages.
 * This class provides methods to put, initialize, and clear MDC entries.
 *
 * @author SunTao
 * @since 2024-05-20
 */
public class LogMdcUtil {
    /**
     * Initializes the MDC.
     */
    public static void initMdc() {
        MDC.put("sysCode", MusesEnvironmentUtil.getSysCode());
        MDC.put("appCode", MusesEnvironmentUtil.getAppCode());
        MDC.put("deployCode", MusesEnvironmentUtil.getDeployCode());
    }

    /**
     * Clears the MDC.
     */
    public static void clearMdc() {
        MDC.remove("sysCode");
        MDC.remove("appCode");
        MDC.remove("deployCode");
    }

    /**
     * Puts a system property value into the MDC.
     *
     * @param mdcKey The key to associate the system property value with in MDC
     */
    public static void putSystemProperty(String mdcKey) {
        MDC.put(mdcKey, System.getProperty(mdcKey));
    }
}
