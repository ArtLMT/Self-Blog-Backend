package com.lmt.selfblog.service;

import com.lmt.selfblog.dto.request.ChapterRequestDTO;
import com.lmt.selfblog.dto.response.AdminChapterResponseDTO;
import com.lmt.selfblog.dto.response.PublicChapterResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ChapterService {

    // Admin Operations
    AdminChapterResponseDTO createChapter(ChapterRequestDTO request);

    AdminChapterResponseDTO updateChapter(UUID id, ChapterRequestDTO request);

    AdminChapterResponseDTO getChapterById(UUID id);

    List<AdminChapterResponseDTO> getChaptersByArcId(UUID arcId);

    void deleteChapter(UUID id);

    // Public Operations
    List<PublicChapterResponseDTO> getPublicChaptersByArc(String arcSlug);

    PublicChapterResponseDTO getPublicChapterBySlug(String slug);

    List<com.lmt.selfblog.dto.response.ChaptersIndexArcDTO> getPublicChaptersIndex();
}
