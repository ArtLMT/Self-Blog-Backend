package com.lmt.selfblog.security;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookiesServiceImpl implements CookiesService {
    public void addAccessToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(15 * 60);
        response.addCookie(cookie);
    }

    public void addRefreshToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
//        cookie.setPath("/api/auth/refresh");
        cookie.setPath("/api/auth");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    public void clearToken(HttpServletResponse response) {
        Cookie access = new Cookie("accessToken", null);
        access.setPath("/");
        access.setMaxAge(0);

        Cookie refresh = new Cookie("refreshToken", null);
//        refresh.setPath("/api/auth/refresh");
        refresh.setPath("/api/auth");
        refresh.setMaxAge(0);

        response.addCookie(access);
        response.addCookie(refresh);
    }

    public String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}