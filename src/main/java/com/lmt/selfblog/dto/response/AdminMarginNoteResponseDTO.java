package com.lmt.selfblog.dto.response;

import com.lmt.selfblog.common.Language;
import com.lmt.selfblog.common.Visibility;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AdminMarginNoteResponseDTO {
    private UUID id;
    private Language language;
    private UUID episodeId;
    private String noteContent;
    private String anchorPosition;
    private Visibility visibility;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private String episodeTitle;
}
