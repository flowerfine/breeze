package cn.sliew.scaleph.api.util;

import cn.sliew.milky.common.util.JacksonUtil;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum RequestParamUtil {
    ;

    private static final List<String> IGNORE_PATH = Arrays.asList("/webjars/**",
            "/doc.html", "/swagger-resources", "/v3/api-docs", "/favicon.ico");
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    /**
     * query param
     */
    public static String getRequestParams(HttpServletRequest request) {
        if (request.getParameterMap().isEmpty()) {
            return "";
        }

        Map<String, Object> query = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            final String key = entry.getKey();
            final String[] value = entry.getValue();

            if (value == null || value.length == 0) {
                query.put(key, null);
            } else if (value.length == 1) {
                query.put(key, value[0]);
            } else {
                query.put(key, value);
            }
        }
        return JacksonUtil.toJsonString(query);
    }

    /**
     * body param
     */
    public static String getRequestBody(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                return payload.replaceAll("\\n", "");
            }
        }
        return "";
    }

    public static boolean isRequestValid(HttpServletRequest request) {
        try {
            new URI(request.getRequestURL().toString());
            return true;
        } catch (URISyntaxException ex) {
            return false;
        }
    }

    public static boolean ignorePath(String uri) {
        return IGNORE_PATH.stream()
                .filter(pattern -> ANT_PATH_MATCHER.match(pattern, uri))
                .findAny()
                .isPresent();
    }
}