package com.lmt.selfblog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Flat JPQL constructor projection used for the public Timeline (table of contents) API.
 * Each instance represents one Arc -> Chapter -> Episode path.
 * The frontend is expected to group rows by arcSlug -> chapterSlug to build the tree.
 */
@Getter
@AllArgsConstructor
public class TimelineItemDTO {
    private String arcTitle;
    private String arcSlug;
    private Integer arcDisplayOrder;
    private String chapterTitle;
    private String chapterSlug;
    private Integer chapterOrderIndex;
    private String episodeTitle;
    private String episodeSlug;
    private Integer episodeOrderIndex;
}
