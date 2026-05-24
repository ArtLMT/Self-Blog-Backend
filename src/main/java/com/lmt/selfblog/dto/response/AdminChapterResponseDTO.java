package com.lmt.selfblog.dto.response;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.Language;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class AdminChapterResponseDTO {
    private UUID id;
    private Language language;
    private UUID arcId;
    private String title;
    private String slug;
    private String quote;
    private String summary;
    private Integer orderIndex;
    private ContentStatus status;
    private Integer readingTimeMinutes;
    private Instant publishedAt;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private Set<AdminEpisodeResponseDTO> episodes;
}
