package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.request.LoginRequestDTO;
import com.lmt.selfblog.dto.request.RegisterRequestDTO;
import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.dto.response.AuthResponseDTO;
import com.lmt.selfblog.security.CookiesService;
import com.lmt.selfblog.service.AuthService;
import com.lmt.selfblog.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user login and registration")
public class AuthController {

    private final AuthService authService;
    private final CookiesService cookiesService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request,
            HttpServletResponse response
    ) {
        AuthResponseDTO authResponse = authService.login(request);
        cookiesService.addRefreshToken(response, authResponse.getRefreshToken());
        cookiesService.addAccessToken(response, authResponse.getAccessToken());

        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
    }

    @PostMapping("/register")
    @Operation(summary = "User registration")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(
            @Valid @RequestBody RegisterRequestDTO request,
            HttpServletResponse response
    ) {
        AuthResponseDTO authResponse = authService.register(request);
        cookiesService.addAccessToken(response, authResponse.getAccessToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful", authResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        cookiesService.clearToken(response);
        authService.logout();
        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookiesService.getRefreshToken(request);

        // Validate and generate new access token (throws if invalid/expired/revoked)
        String newAccessToken = refreshTokenService.generateAccessToken(refreshToken);

        // Set new access token cookie
        cookiesService.addAccessToken(response, newAccessToken);

        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", null));
    }
}
