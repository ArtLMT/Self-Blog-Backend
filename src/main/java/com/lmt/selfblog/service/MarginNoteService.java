package com.lmt.selfblog.service;

import com.lmt.selfblog.dto.request.MarginNoteRequestDTO;
import com.lmt.selfblog.dto.response.AdminMarginNoteResponseDTO;
import com.lmt.selfblog.dto.response.PublicMarginNoteResponseDTO;

import java.util.List;
import java.util.UUID;

public interface MarginNoteService {

    // Admin Operations
    AdminMarginNoteResponseDTO createMarginNote(MarginNoteRequestDTO request);

    AdminMarginNoteResponseDTO updateMarginNote(UUID id, MarginNoteRequestDTO request);

    AdminMarginNoteResponseDTO getMarginNoteById(UUID id);

    List<AdminMarginNoteResponseDTO> getMarginNotesByEpisodeId(UUID episodeId);

    void deleteMarginNote(UUID id);

    // Public Operations
    List<PublicMarginNoteResponseDTO> getPublicMarginNotesForEpisode(String episodeSlug);
}
