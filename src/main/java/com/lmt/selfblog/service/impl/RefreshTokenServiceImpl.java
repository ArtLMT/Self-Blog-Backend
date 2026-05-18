package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.entity.RefreshToken;
import com.lmt.selfblog.entity.User;
import com.lmt.selfblog.exception.NotFoundException;
import com.lmt.selfblog.exception.UnauthorizedException;
import com.lmt.selfblog.repository.RefreshTokenRepository;
import com.lmt.selfblog.security.JwtUtils;
import com.lmt.selfblog.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${jwt.expiration.refresh}")
    private long jwtExpirationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    @Override
    public String generateRefreshToken(User user) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plusMillis(jwtExpirationMs))
                .build();

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    @Override
    public String validateRefreshToken(String refreshToken) {
        if (refreshToken == null) {
            throw new UnauthorizedException(
                    ErrorCode.REFRESH_TOKEN_NOT_FOUND,
                    "No refresh token provided"
            );
        }

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken);

        if (refreshTokenEntity == null) {
            throw new NotFoundException(
                    ErrorCode.REFRESH_TOKEN_NOT_FOUND,
                    "Refresh token not found in database"
            );
        }

        if (refreshTokenEntity.isRevoked()) {
            throw new UnauthorizedException(
                    ErrorCode.REFRESH_TOKEN_REVOKED,
                    "Refresh token has been revoked"
            );
        }

        if (refreshTokenEntity.getExpiryDate().isBefore(Instant.now())) {
            throw new UnauthorizedException(
                    ErrorCode.REFRESH_TOKEN_EXPIRED,
                    "Refresh token has expired"
            );
        }

        return refreshTokenEntity.getUser().getUsername();
    }

    @Override
    public String generateAccessToken(String refreshToken) {
        // validateRefreshToken will throw if invalid
        String username = validateRefreshToken(refreshToken);
        return jwtUtils.generateJwtToken(username);
    }

    @Override
    public Void revokeRefreshToken(User user) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUser_IdAndRevokedIsFalse(user.getId());
        // Hien tai thi hard delete luon
        if (refreshTokenEntity != null) {
            refreshTokenRepository.delete(refreshTokenEntity);
        }
        return null;
    }
}
