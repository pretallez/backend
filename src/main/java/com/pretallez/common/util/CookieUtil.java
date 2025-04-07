package com.pretallez.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;
import java.util.Arrays;

public class CookieUtil {

    /**
     * 쿠키 생성 및 응답에 추가 (SameSite 지원)
     */
    public static void addCookie(HttpServletResponse response,
                                 String name,
                                 String value,
                                 Duration maxAge,
                                 boolean httpOnly,
                                 boolean secure,
                                 String path,
                                 String sameSite) {

        StringBuilder cookieBuilder = new StringBuilder();

        cookieBuilder.append(name).append("=").append(value).append(";");
        cookieBuilder.append("Path=").append(path != null ? path : "/").append(";");
        cookieBuilder.append("Max-Age=").append(maxAge.getSeconds()).append(";");

        if (httpOnly) cookieBuilder.append("HttpOnly;");
        if (secure) cookieBuilder.append("Secure;");
        if (sameSite != null) cookieBuilder.append("SameSite=").append(sameSite).append(";");

        response.addHeader("Set-Cookie", cookieBuilder.toString());
    }

    /**
     * 쿠키 조회
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    /**
     * 쿠키 삭제 (maxAge=0)
     */
    public static void deleteCookie(HttpServletResponse response,
                                    String name,
                                    boolean httpOnly,
                                    boolean secure,
                                    String path,
                                    String sameSite) {
        addCookie(response, name, "", Duration.ZERO, httpOnly, secure, path, sameSite);
    }
}
