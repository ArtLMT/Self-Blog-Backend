package com.lmt.selfblog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Payload for user login")
public class LoginRequestDTO {

    @NotBlank(message = "Username is required")
    @Schema(description = "Username of the account", example = "admin")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Account password", example = "secret123")
    private String password;
}
