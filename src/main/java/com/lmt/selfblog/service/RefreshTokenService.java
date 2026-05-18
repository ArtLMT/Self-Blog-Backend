package com.lmt.selfblog.service;

import com.lmt.selfblog.entity.User;

public interface RefreshTokenService {
    String generateRefreshToken(User user);
    String validateRefreshToken(String refreshToken);
    String generateAccessToken(String refreshToken);
    Void revokeRefreshToken(User user);
}
