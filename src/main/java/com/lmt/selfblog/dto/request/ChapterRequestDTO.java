package com.lmt.selfblog.dto.request;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.Language;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Schema(description = "Payload for creating or updating a Chapter")
public class ChapterRequestDTO {

    @NotNull(message = "Language is required")
    @Schema(description = "Language of the translation", example = "EN")
    private Language language;

    @NotNull(message = "Arc ID is required")
    @Schema(description = "UUID of the parent Arc", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID arcId;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    @Schema(description = "Title of the chapter", example = "Finding Stillness")
    private String title;

    @NotBlank(message = "Slug is required")
    @Size(max = 255, message = "Slug cannot exceed 255 characters")
    @Schema(description = "Unique URL-friendly slug", example = "finding-stillness")
    private String slug;

    @Size(max = 500, message = "Quote cannot exceed 500 characters")
    @Schema(description = "Introductory quote for the chapter", example = "Stillness is the key.")
    private String quote;

    @Size(max = 1000, message = "Summary cannot exceed 1000 characters")
    @Schema(description = "Short summary of the chapter", example = "Learning how to sit with my thoughts.")
    private String summary;

    @NotNull(message = "Order index is required")
    @Schema(description = "Chapter ordering sequence within the Arc", example = "1")
    private Integer orderIndex;

    @NotNull(message = "Status is required")
    @Schema(description = "Chapter editorial status", example = "PUBLISHED")
    private ContentStatus status;

    @Schema(description = "Calculated reading time in minutes (optional)")
    private Integer readingTimeMinutes;

    @Schema(description = "Publish timestamp (optional)")
    private Instant publishedAt;
}
