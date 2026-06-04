package com.lmt.selfblog.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class ChaptersIndexArcDTO {
    private String slug;
    private String title;
    private List<ChaptersIndexChapterDTO> chapters;
}
