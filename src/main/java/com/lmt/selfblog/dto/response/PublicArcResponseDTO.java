package com.lmt.selfblog.dto.response;

import com.lmt.selfblog.common.Language;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class PublicArcResponseDTO {
    private Language language;
    private String title;
    private String slug;
    private String summary;
    private Integer displayOrder;
    private Instant startDate;
    private Instant endDate;
    private Set<PublicChapterResponseDTO> chapters;
}
