package util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getString(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }

    public static Integer getInt(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        if (value == null || value.trim().isEmpty()) return 0;
        return Integer.parseInt(value);
    }

    public static Double getDouble(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        if (value == null || value.trim().isEmpty()) return 0.0;
        return Double.parseDouble(value);
    }
}