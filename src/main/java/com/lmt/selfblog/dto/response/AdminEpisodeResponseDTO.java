package com.lmt.selfblog.dto.response;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.Language;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class AdminEpisodeResponseDTO {
    private UUID id;
    private Language language;
    private UUID chapterId;
    private String title;
    private String slug;
    private String markdownContent;
    private String renderedContent;
    private Integer orderIndex;
    private String conclusion;
    private Instant eventDate;
    private ContentStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private Set<AdminMarginNoteResponseDTO> marginNotes;
}
