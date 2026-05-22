package com.lmt.selfblog.service;

import com.lmt.selfblog.dto.request.EpisodeRequestDTO;
import com.lmt.selfblog.dto.response.AdminEpisodeResponseDTO;
import com.lmt.selfblog.dto.response.PublicEpisodeResponseDTO;

import java.util.List;
import java.util.UUID;

public interface EpisodeService {

    // Admin Operations
    AdminEpisodeResponseDTO createEpisode(EpisodeRequestDTO request);

    AdminEpisodeResponseDTO updateEpisode(UUID id, EpisodeRequestDTO request);

    AdminEpisodeResponseDTO getEpisodeById(UUID id);

    List<AdminEpisodeResponseDTO> getEpisodesByChapterId(UUID chapterId);

    void deleteEpisode(UUID id);

    // Public Operations
    List<PublicEpisodeResponseDTO> getPublicEpisodesByChapter(String chapterSlug);

    PublicEpisodeResponseDTO getPublicEpisodeBySlug(String slug);
}
