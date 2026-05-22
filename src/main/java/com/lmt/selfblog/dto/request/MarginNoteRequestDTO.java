package com.lmt.selfblog.dto.request;

import com.lmt.selfblog.common.Visibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Payload for creating or updating a Margin Note")
public class MarginNoteRequestDTO {

    @NotNull(message = "Episode ID is required")
    @Schema(description = "UUID of the parent Episode")
    private UUID episodeId;

    @NotBlank(message = "Note content is required")
    @Schema(description = "Markdown/Text content of the margin note")
    private String noteContent;

    @NotBlank(message = "Anchor position is required")
    @Size(max = 255, message = "Anchor position cannot exceed 255 characters")
    @Schema(description = "CSS selector or text anchor reference position in the episode content", example = "para-2-line-4")
    private String anchorPosition;

    @NotNull(message = "Visibility is required")
    @Schema(description = "Margin note visibility status", example = "PUBLIC")
    private Visibility visibility;
}
