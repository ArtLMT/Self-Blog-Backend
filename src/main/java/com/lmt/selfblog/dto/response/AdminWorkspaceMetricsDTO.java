package com.lmt.selfblog.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class AdminWorkspaceMetricsDTO {
    private long draftsCount;
    private long publishedChaptersCount;
    private long unfinishedArcsCount;
    private List<RecentlyEditedEpisodeDTO> recentlyEditedEpisodes;
}
