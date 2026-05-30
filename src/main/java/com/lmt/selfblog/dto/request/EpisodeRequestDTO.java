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
@Schema(description = "Payload for creating or updating an Episode")
public class EpisodeRequestDTO {

    @NotNull(message = "Language is required")
    @Schema(description = "Language of the translation", example = "EN")
    private Language language;

    @NotNull(message = "Chapter ID is required")
    @Schema(description = "UUID of the parent Chapter")
    private UUID chapterId;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    @Schema(description = "Title of the episode", example = "The First Breath")
    private String title;

    @NotBlank(message = "Slug is required")
    @Size(max = 255, message = "Slug cannot exceed 255 characters")
    @Schema(description = "Unique URL-friendly slug", example = "the-first-breath")
    private String slug;

    @NotBlank(message = "Markdown content is required")
    @Schema(description = "Original Markdown format content")
    private String markdownContent;


    @NotNull(message = "Order index is required")
    @Schema(description = "Episode ordering sequence within the Chapter", example = "1")
    private Integer orderIndex;

    @Size(max = 1000, message = "Conclusion cannot exceed 1000 characters")
    @Schema(description = "Episode closing thoughts/conclusion")
    private String conclusion;

    @NotNull(message = "Event date is required")
    @Schema(description = "The specific date/time when this narrative event took place")
    private Instant eventDate;

    @NotNull(message = "Status is required")
    @Schema(description = "Episode publication status", example = "DRAFT")
    private ContentStatus status;
}
