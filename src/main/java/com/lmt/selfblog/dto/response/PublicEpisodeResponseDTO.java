package com.lmt.selfblog.dto.response;

import com.lmt.selfblog.common.Language;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class PublicEpisodeResponseDTO {
    private Language language;
    private String title;
    private String slug;
    private String markdownContent;
    private Integer orderIndex;
    private String conclusion;
    private Instant eventDate;
    private String chapterSlug;
    private Set<PublicMarginNoteResponseDTO> marginNotes;
}
