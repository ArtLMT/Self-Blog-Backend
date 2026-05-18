package com.lmt.selfblog.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookiesService {
    void addAccessToken(HttpServletResponse response, String accessToken);
    void addRefreshToken(HttpServletResponse response, String refreshToken);
    void clearToken(HttpServletResponse response);
    String getRefreshToken(HttpServletRequest request);
}
