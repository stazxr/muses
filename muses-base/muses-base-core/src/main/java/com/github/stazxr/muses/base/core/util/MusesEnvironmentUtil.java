package com.github.stazxr.muses.base.core.util;

import com.github.stazxr.muses.base.core.properties.MusesHeaderProperties;
import com.github.stazxr.muses.utils.base.StringUtil;
import com.github.stazxr.muses.utils.base.net.IpUtil;
import com.github.stazxr.muses.utils.log.constants.MarkerConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * Muses environment variable utility class for managing Muses-related environment variable information.
 * <p>
 * This utility class provides methods to retrieve system code, application code, and service deployment code,
 * as well as functionality to set the MusesHeaderProperties object.
 * </p>
 *
 * @since 2024-05-05
 * @author SunTao
 */
@Slf4j
public class MusesEnvironmentUtil {
    private static String deployCode;

    private static MusesHeaderProperties headerProperties;

    /**
     * Sets the MusesHeaderProperties object.
     * <p>
     * This method sets the MusesHeaderProperties object and determines the deployment code based on deployment information.
     * </p>
     *
     * @param headerProperties The MusesHeaderProperties object
     */
    public static void setHeaderProperties(MusesHeaderProperties headerProperties) {
        if (MusesEnvironmentUtil.headerProperties == null) {
            MusesEnvironmentUtil.headerProperties = headerProperties;

            // Set deployment code
            MusesHeaderProperties.Deploy deploy = MusesEnvironmentUtil.headerProperties.getDeploy();
            if (deploy != null && StringUtil.isNotBlank(deploy.getDeployIp())) {
                deployCode = IpUtil.get7CharFromIpString(deploy.getDeployIp(), deploy.getDeployUnit());
                log.info(MarkerConstants.MONITOR_MARKER, "Set application deployment code information: " + deployCode);
            }
        }
    }

    /**
     * Retrieves the system code.
     *
     * @return The system code
     */
    public static String getSysCode() {
        return headerProperties.getSysCode();
    }

    /**
     * Retrieves the application code.
     *
     * @return The application code
     */
    public static String getAppCode() {
        return headerProperties.getAppCode();
    }

    /**
     * Retrieves the deployment code.
     *
     * @return The deployment code
     */
    public static String getDeployCode() {
        return deployCode;
    }
}
