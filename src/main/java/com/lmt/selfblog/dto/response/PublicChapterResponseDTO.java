package com.lmt.selfblog.dto.response;

import com.lmt.selfblog.common.Language;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class PublicChapterResponseDTO {
    private Language language;
    private String title;
    private String slug;
    private String quote;
    private String summary;
    private Integer orderIndex;
    private Integer readingTimeMinutes;
    private Instant publishedAt;
    private String arcSlug;
    private Set<PublicEpisodeResponseDTO> episodes;
}
