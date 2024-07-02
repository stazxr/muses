package com.github.stazxr.muses.base.core.util;

import com.github.stazxr.muses.base.core.context.MusesContext;
import com.github.stazxr.muses.base.core.entity.MusesContextTag;
import com.github.stazxr.muses.base.core.properties.MusesContextProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Utility class for managing Muses context based on HTTP request headers.
 *
 * @author SunTao
 * @since 2024-07-02
 */
public class MusesContextUtil {
    /**
     * Creates and sets MusesCoreContext based on HTTP request headers.
     *
     * @param request           HttpServletRequest representing the client request
     * @param contextProperties MusesContextProperties object used for initializing context
     */
    public static void createContext(HttpServletRequest request, MusesContextProperties contextProperties) {
        List<String> tagNames = contextProperties.getTagNames();
        List<String> headerNames = getRequestHeaderNames(request);
        List<String> musesHeaderNames = filterHeaderNames(headerNames, tagNames);
        List<MusesContextTag> reqTags = new ArrayList<>();
        for (String musesHeaderName : musesHeaderNames) {
            String headerVar = request.getHeader(musesHeaderName);
            if (headerVar != null) {
                reqTags.add(new MusesContextTag(musesHeaderName, headerVar));
            }
        }
        MusesContext.putAll(reqTags);
    }

    /**
     * Filters header names based on configured tag names.
     *
     * @param headerNames List of header names from the HttpServletRequest
     * @param tagNames    List of tag names configured in MusesContextProperties
     * @return List of filtered header names that match tag names
     */
    private static List<String> filterHeaderNames(List<String> headerNames, List<String> tagNames) {
        List<String> result = new ArrayList<>();
        for (String headerName : headerNames) {
            if (tagNames.contains(headerName)) {
                result.add(headerName);
            }
        }
        return result;
    }

    /**
     * Retrieves all header names from the HttpServletRequest.
     *
     * @param request HttpServletRequest object representing the client request
     * @return List of all header names from the HttpServletRequest
     */
    private static List<String> getRequestHeaderNames(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        List<String> result = new ArrayList<>();
        while (headerNames.hasMoreElements()) {
            result.add(headerNames.nextElement());
        }
        return result;
    }
}
