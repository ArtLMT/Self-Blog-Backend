package com.lmt.selfblog.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequestDTO {
    @NotBlank(message = "English title is required")
    private String titleEn;

    @NotBlank(message = "Vietnamese title is required")
    private String titleVi;

    @NotBlank(message = "English content is required")
    private String contentEn;

    @NotBlank(message = "Vietnamese content is required")
    private String contentVi;

    private boolean published;
}
