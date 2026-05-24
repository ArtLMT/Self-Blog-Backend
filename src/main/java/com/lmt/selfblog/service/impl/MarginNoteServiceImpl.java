package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.common.Language;
import com.lmt.selfblog.common.LanguageResolver;
import com.lmt.selfblog.dto.request.MarginNoteRequestDTO;
import com.lmt.selfblog.dto.response.AdminMarginNoteResponseDTO;
import com.lmt.selfblog.entity.Episode;
import com.lmt.selfblog.entity.MarginNote;
import com.lmt.selfblog.entity.MarginNoteTranslation;
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
    private final LanguageResolver languageResolver;

    @Override
    public AdminMarginNoteResponseDTO createMarginNote(MarginNoteRequestDTO request) {
        Episode episode = episodeRepository.findById(request.getEpisodeId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EPISODE_NOT_FOUND, "Episode not found with ID: " + request.getEpisodeId()));

        MarginNote marginNote = marginNoteMapper.toEntity(request);
        marginNote.setEpisode(episode);
        
        MarginNoteTranslation translation = new MarginNoteTranslation();
        translation.setLanguage(request.getLanguage());
        translation.setNoteContent(request.getNoteContent());
        marginNote.addTranslation(translation);

        MarginNote saved = marginNoteRepository.save(marginNote);
        return marginNoteMapper.toAdminDto(saved, request.getLanguage());
    }

    @Override
    public AdminMarginNoteResponseDTO updateMarginNote(UUID id, MarginNoteRequestDTO request) {
        MarginNote marginNote = marginNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MARGIN_NOTE_NOT_FOUND, "Margin Note not found with ID: " + id));

        Episode episode = episodeRepository.findById(request.getEpisodeId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EPISODE_NOT_FOUND, "Episode not found with ID: " + request.getEpisodeId()));

        marginNoteMapper.updateEntity(request, marginNote);
        marginNote.setEpisode(episode);
        
        MarginNoteTranslation translation = marginNote.getTranslations().stream()
                .filter(t -> t.getLanguage() == request.getLanguage())
                .findFirst()
                .orElseGet(() -> {
                    MarginNoteTranslation newTrans = new MarginNoteTranslation();
                    newTrans.setLanguage(request.getLanguage());
                    marginNote.addTranslation(newTrans);
                    return newTrans;
                });
                
        translation.setNoteContent(request.getNoteContent());

        MarginNote saved = marginNoteRepository.save(marginNote);
        return marginNoteMapper.toAdminDto(saved, request.getLanguage());
    }

    @Override
    @Transactional(readOnly = true)
    public AdminMarginNoteResponseDTO getMarginNoteById(UUID id) {
        MarginNote marginNote = marginNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MARGIN_NOTE_NOT_FOUND, "Margin Note not found with ID: " + id));
        Language lang = languageResolver.resolveLanguage();
        return marginNoteMapper.toAdminDto(marginNote, lang);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminMarginNoteResponseDTO> getMarginNotesByEpisodeId(UUID episodeId) {
        Language lang = languageResolver.resolveLanguage();
        return marginNoteRepository.findByEpisodeId(episodeId).stream()
                .map(note -> marginNoteMapper.toAdminDto(note, lang))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMarginNote(UUID id) {
        MarginNote marginNote = marginNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MARGIN_NOTE_NOT_FOUND, "Margin Note not found with ID: " + id));
        marginNoteRepository.delete(marginNote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<com.lmt.selfblog.dto.response.PublicMarginNoteResponseDTO> getPublicMarginNotesForEpisode(String episodeSlug) {
        Language lang = languageResolver.resolveLanguage();
        return marginNoteRepository.findByEpisodeSlugAndVisibilityIn(episodeSlug, List.of(com.lmt.selfblog.common.Visibility.PUBLIC)).stream()
                .map(note -> marginNoteMapper.toPublicDto(note, lang))
                .collect(Collectors.toList());
    }
}
