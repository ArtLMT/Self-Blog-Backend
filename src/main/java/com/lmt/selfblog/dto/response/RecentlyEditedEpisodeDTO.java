package com.lmt.selfblog.dto.response;

import com.lmt.selfblog.common.ContentStatus;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class RecentlyEditedEpisodeDTO {
    private UUID id;
    private String title;
    private ContentStatus status;
    private Instant updatedAt;
    private String contentSnippet;
}
