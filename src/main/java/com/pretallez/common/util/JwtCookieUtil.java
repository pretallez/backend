package com.pretallez.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;

@Component
public class JwtCookieUtil {

    private static final Duration EXPIRY = Duration.ofDays(7);
    private static final String COOKIE_NAME = "accessToken";

    /**
     * 쿠키 생성 및 응답에 추가 (SameSite 지원)
     */
    public void addJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setHttpOnly(true); // JS 접근 차단
//        cookie.setSecure(true);   // HTTPS에서만 전송
        cookie.setPath("/");
        cookie.setMaxAge((int) EXPIRY.getSeconds());

        response.addCookie(cookie);
    }

    /**
     * 쿠키 조회
     */
    public String getCookieValue(HttpServletRequest request, String name) {
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
    public void deleteJwtCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
