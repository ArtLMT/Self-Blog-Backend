package com.lmt.selfblog.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
public class PostResponseDTO {
    private Long id;
    private String titleEn;
    private String titleVi;
    private String contentEn;
    private String contentVi;
    private boolean published;
    private Instant createdAt;
    private Instant updatedAt;
}
