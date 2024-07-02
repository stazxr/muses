package com.github.stazxr.muses.base.core.context;

import com.github.stazxr.muses.base.core.constants.MusesTagConstants;
import com.github.stazxr.muses.base.core.entity.MusesContextTag;
import com.github.stazxr.muses.base.core.properties.MusesContextProperties;
import com.github.stazxr.muses.base.core.util.MusesEnvironmentUtil;
import com.github.stazxr.muses.base.core.util.SpringContextUtil;

/**
 * Manages a thread-local instance of {@link MusesCoreContext}.
 * Provides methods to get, set, and remove the context for the current thread.
 * Uses {@link ThreadLocal} for thread safety.
 *
 * @author SunTao
 * @since 2024-07-02
 */
public class MusesCoreContextHolder {

    private static final ThreadLocal<MusesCoreContext> CONTEXT = new ThreadLocal<>();

    /**
     * Retrieves the current thread's {@link MusesCoreContext} instance.
     * Creates a new instance if none exists for the current thread.
     *
     * @return The current thread's {@link MusesCoreContext} instance
     */
    public static MusesCoreContext get() {
        MusesCoreContext coreContext = CONTEXT.get();
        if (coreContext == null) {
            MusesContextProperties musesContextProperties = SpringContextUtil.getBean(MusesContextProperties.class);
            coreContext = new MusesCoreContext(musesContextProperties);
            addSystemTags(coreContext);
            // Set the context for the current thread
            set(coreContext);
        }
        return coreContext;
    }

    /**
     * Sets the {@link MusesCoreContext} instance for the current thread.
     *
     * @param coreContext The {@link MusesCoreContext} instance to set
     */
    public static void set(MusesCoreContext coreContext) {
        CONTEXT.set(coreContext);
    }

    /**
     * Removes the {@link MusesCoreContext} instance for the current thread.
     */
    public static void remove() {
        CONTEXT.remove();
    }

    /**
     * Adds system-related tags to the given {@link MusesCoreContext}.
     *
     * @param coreContext The {@link MusesCoreContext} to add tags to
     */
    private static void addSystemTags(MusesCoreContext coreContext) {
        String sysCode = MusesEnvironmentUtil.getSysCode();
        if (sysCode != null) {
            coreContext.put(new MusesContextTag(MusesTagConstants.MUSES_SYS_CODE_TAG, sysCode));
        }

        String appCode = MusesEnvironmentUtil.getAppCode();
        if (appCode != null) {
            coreContext.put(new MusesContextTag(MusesTagConstants.MUSES_APP_CODE_TAG, appCode));
        }

        String deployCode = MusesEnvironmentUtil.getDeployCode();
        if (deployCode != null) {
            coreContext.put(new MusesContextTag(MusesTagConstants.MUSES_DEPLOY_CODE_TAG, deployCode));
        }
    }
}
