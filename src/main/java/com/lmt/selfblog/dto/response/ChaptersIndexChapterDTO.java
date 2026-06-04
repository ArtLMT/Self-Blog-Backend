package com.lmt.selfblog.dto.response;

import lombok.Data;

@Data
public class ChaptersIndexChapterDTO {
    private String slug;
    private String title;
    private Integer orderIndex;
    private Integer readingTimeMinutes;
}
