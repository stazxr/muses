package com.github.stazxr.muses.base.log.advice;

import com.github.stazxr.muses.base.log.cache.LogControlPathCacheManager;
import com.github.stazxr.muses.base.log.context.LogControlSerNoContext;
import com.github.stazxr.muses.base.log.properties.LogControlProperties;
import com.github.stazxr.muses.utils.mask.MaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Response body advice for controlling and logging outgoing responses.
 * This advice handles masking sensitive data based on configured paths.
 *
 * @author SunTao
 * @since 2024-05-16
 */
@Slf4j
@RestControllerAdvice
public class ResLogControlAdvice implements ResponseBodyAdvice<Object>, Ordered {
    private HttpServletRequest httpServletRequest;

    private LogControlProperties logControlProperties;

    private LogControlPathCacheManager logControlPathCacheManager;

    /**
     * Checks if the advice supports processing based on configuration.
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return logControlProperties.isEnabled();
    }

    /**
     * Executes actions before writing the response body, logging the masked response.
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            String simpleName = returnType.getContainingClass().getSimpleName();
            String methodName = returnType.getMethod() == null ? "" : returnType.getMethod().getName();
            String serNo = LogControlSerNoContext.exist() ? "[" + LogControlSerNoContext.get() + "]" : "";
            if (logControlPathCacheManager.enabledLog(httpServletRequest.getServletPath())) {
                log.info("{}.{}{} response body is: {}", simpleName, methodName, serNo, MaskUtil.toMaskString(body));
            }
        } catch (Exception e) {
            log.error("Error logging response body", e);
        } finally {
            LogControlSerNoContext.clear();
        }

        return body;
    }

    /**
     * Sets the HttpServletRequest instance via dependency injection.
     */
    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * Sets the LogControlProperties instance via dependency injection.
     */
    @Autowired
    public void setLogControlProperties(LogControlProperties logControlProperties) {
        this.logControlProperties = logControlProperties;
    }

    /**
     * Sets the LogControlPathCacheManager instance via dependency injection.
     */
    @Autowired
    public void setLogControlPathCacheManager(LogControlPathCacheManager logControlPathCacheManager) {
        this.logControlPathCacheManager = logControlPathCacheManager;
    }

    /**
     * Specifies the order in which this advice is applied.
     */
    @Override
    public int getOrder() {
        return 999;
    }
}
