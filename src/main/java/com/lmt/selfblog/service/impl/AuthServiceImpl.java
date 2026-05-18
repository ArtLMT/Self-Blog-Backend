package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.dto.request.LoginRequestDTO;
import com.lmt.selfblog.dto.request.RegisterRequestDTO;
import com.lmt.selfblog.dto.response.AuthResponseDTO;
import com.lmt.selfblog.entity.RefreshToken;
import com.lmt.selfblog.exception.ConflictException;
import com.lmt.selfblog.exception.UnauthorizedException;
import com.lmt.selfblog.entity.Role;
import com.lmt.selfblog.entity.User;
import com.lmt.selfblog.repository.UserRepository;
import com.lmt.selfblog.security.CookiesService;
import com.lmt.selfblog.security.JwtUtils;
import com.lmt.selfblog.service.AuthService;
import com.lmt.selfblog.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CookiesService cookiesService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new UnauthorizedException(
                                ErrorCode.INVALID_CREDENTIALS,
                                "Username not found: " + request.getUsername()
                        )
                );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException(
                    ErrorCode.INVALID_CREDENTIALS,
                    "Wrong password for username: " + request.getUsername()
            );
        }

        String accessToken = jwtUtils.generateJwtToken(user.getUsername());
        refreshTokenService.revokeRefreshToken(user);
        String refreshToken = refreshTokenService.generateRefreshToken(user);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
//                .tokenType("Bearer")
//                .username(user.getUsername())
//                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {

            throw new ConflictException(
                    ErrorCode.USERNAME_ALREADY_EXISTS,
                    "Duplicate username: " + request.getUsername()
            );
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new ConflictException(
                    ErrorCode.EMAIL_ALREADY_EXISTS,
                    "Duplicate email: " + request.getEmail()
            );
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String accessToken = jwtUtils.generateJwtToken(user.getUsername());
        String refreshToken = refreshTokenService.generateRefreshToken(user);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
//                .tokenType("Bearer")
//                .username(user.getUsername())
//                .role(user.getRole().name())
                .build();
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() ->
                            new RuntimeException("User not found with username: " + username)
                    );
            refreshTokenService.revokeRefreshToken(user);
        }
    }
}
