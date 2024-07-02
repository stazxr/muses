package com.github.stazxr.muses.base.core.filter;

import com.github.stazxr.muses.base.core.context.MusesCoreContextHolder;
import com.github.stazxr.muses.base.core.properties.MusesContextProperties;
import com.github.stazxr.muses.base.core.util.MusesContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet filter that manages application-specific context.
 * Clears the context after request processing completes.
 *
 * @author SunTao
 * @since 2024-07-02
 */
@Component
public class MusesContextFilter extends OncePerRequestFilter {
    private MusesContextProperties contextProperties;

    /**
     * Sets the MusesContextProperties instance.
     *
     * @param contextProperties The MusesContextProperties instance to set
     */
    @Autowired
    public void setContextProperties(MusesContextProperties contextProperties) {
        this.contextProperties = contextProperties;
    }

    /**
     * Core method of the filter. Sets up context on request entry and cleans up after request processing.
     *
     * @param request  HttpServletRequest representing the client request
     * @param response HttpServletResponse representing the server response
     * @param filterChain FilterChain for invoking the next filter or target resource
     * @throws ServletException in case of a servlet exception
     * @throws IOException in case of an I/O error
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            MusesContextUtil.createContext(request, contextProperties);
            filterChain.doFilter(request, response);
        } finally {
            MusesCoreContextHolder.remove();
        }
    }
}
