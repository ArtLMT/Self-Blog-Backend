package com.lmt.selfblog.dto.response;

import com.lmt.selfblog.common.Language;
import lombok.Data;

@Data
public class PublicMarginNoteResponseDTO {
    private Language language;
    private String noteContent;
    private String anchorPosition;
    private String episodeSlug;
}
