package com.lmt.selfblog.dto.request;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.Visibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

@Data
@Schema(description = "Payload for creating or updating an Arc")
public class ArcRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    @Schema(description = "Title of the arc", example = "The Wilderness Years")
    private String title;

    @NotBlank(message = "Slug is required")
    @Size(max = 255, message = "Slug cannot exceed 255 characters")
    @Schema(description = "Unique URL-friendly slug", example = "the-wilderness-years")
    private String slug;

    @Size(max = 1000, message = "Summary cannot exceed 1000 characters")
    @Schema(description = "Short summary of the arc", example = "A period of self-reflection and growth.")
    private String summary;

    @NotNull(message = "Display order is required")
    @Schema(description = "Display sorting order", example = "1")
    private Integer displayOrder;

    @NotNull(message = "Start date is required")
    @Schema(description = "Start date/time of the arc")
    private Instant startDate;

    @Schema(description = "End date/time of the arc (optional)")
    private Instant endDate;

    @NotNull(message = "Visibility is required")
    @Schema(description = "Arc visibility setting", example = "PUBLIC")
    private Visibility visibility;

    @NotNull(message = "Status is required")
    @Schema(description = "Arc lifecycle status", example = "PUBLISHED")
    private ContentStatus status;
}
