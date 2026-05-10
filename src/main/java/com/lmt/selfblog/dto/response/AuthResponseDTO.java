package com.lmt.selfblog.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Authentication response containing JWT token and user details")
public class AuthResponseDTO {

    @Schema(description = "JWT access token")
    private String token;

    @Schema(description = "Token type", example = "Bearer")
    private String tokenType;

    @Schema(description = "Username of the authenticated user")
    private String username;

    @Schema(description = "Role of the authenticated user")
    private String role;
}
