package com.lmt.selfblog.dto.response;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.Language;
import com.lmt.selfblog.common.Visibility;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AdminArcResponseDTO {
    private UUID id;
    private Language language;
    private String title;
    private String slug;
    private String summary;
    private Integer displayOrder;
    private Instant startDate;
    private Instant endDate;
    private Visibility visibility;
    private ContentStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
