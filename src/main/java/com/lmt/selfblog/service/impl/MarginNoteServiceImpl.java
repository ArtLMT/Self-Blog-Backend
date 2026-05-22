package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.common.Visibility;
import com.lmt.selfblog.dto.request.MarginNoteRequestDTO;
import com.lmt.selfblog.dto.response.AdminMarginNoteResponseDTO;
import com.lmt.selfblog.dto.response.PublicMarginNoteResponseDTO;
import com.lmt.selfblog.entity.Episode;
import com.lmt.selfblog.entity.MarginNote;
import com.lmt.selfblog.exception.NotFoundException;
import com.lmt.selfblog.mapper.MarginNoteMapper;
import com.lmt.selfblog.repository.EpisodeRepository;
import com.lmt.selfblog.repository.MarginNoteRepository;
import com.lmt.selfblog.service.MarginNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MarginNoteServiceImpl implements MarginNoteService {

    private final MarginNoteRepository marginNoteRepository;
    private final EpisodeRepository episodeRepository;

    private final MarginNoteMapper marginNoteMapper;

    @Override
    public AdminMarginNoteResponseDTO createMarginNote(MarginNoteRequestDTO request) {
        Episode episode = episodeRepository.findById(request.getEpisodeId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EPISODE_NOT_FOUND, "Episode not found with ID: " + request.getEpisodeId()));

        MarginNote note = marginNoteMapper.toEntity(request);
        note.setEpisode(episode);
        MarginNote saved = marginNoteRepository.save(note);
        return marginNoteMapper.toAdminDto(saved);
    }

    @Override
    public AdminMarginNoteResponseDTO updateMarginNote(UUID id, MarginNoteRequestDTO request) {
        MarginNote note = marginNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MARGIN_NOTE_NOT_FOUND, "Margin Note not found with ID: " + id));

        Episode episode = episodeRepository.findById(request.getEpisodeId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EPISODE_NOT_FOUND, "Episode not found with ID: " + request.getEpisodeId()));

        marginNoteMapper.updateEntity(request, note);
        note.setEpisode(episode);
        MarginNote saved = marginNoteRepository.save(note);
        return marginNoteMapper.toAdminDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminMarginNoteResponseDTO getMarginNoteById(UUID id) {
        MarginNote note = marginNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MARGIN_NOTE_NOT_FOUND, "Margin Note not found with ID: " + id));
        return marginNoteMapper.toAdminDto(note);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminMarginNoteResponseDTO> getMarginNotesByEpisodeId(UUID episodeId) {
        return marginNoteRepository.findByEpisodeId(episodeId).stream()
                .map(marginNoteMapper::toAdminDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMarginNote(UUID id) {
        MarginNote note = marginNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MARGIN_NOTE_NOT_FOUND, "Margin Note not found with ID: " + id));
        // Soft delete: set visibility to PRIVATE
        note.setVisibility(Visibility.PRIVATE);
        marginNoteRepository.save(note);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicMarginNoteResponseDTO> getPublicMarginNotesForEpisode(String episodeSlug) {
        return marginNoteRepository.findByEpisodeSlugAndVisibilityIn(episodeSlug, List.of(Visibility.PUBLIC))
                .stream()
                .map(marginNoteMapper::toPublicDto)
                .collect(Collectors.toList());
    }
}
