package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.common.Visibility;
import com.lmt.selfblog.dto.request.EpisodeRequestDTO;
import com.lmt.selfblog.dto.response.AdminEpisodeResponseDTO;
import com.lmt.selfblog.dto.response.PublicEpisodeResponseDTO;
import com.lmt.selfblog.dto.response.PublicMarginNoteResponseDTO;
import com.lmt.selfblog.entity.Chapter;
import com.lmt.selfblog.entity.Episode;
import com.lmt.selfblog.exception.ConflictException;
import com.lmt.selfblog.exception.NotFoundException;
import com.lmt.selfblog.mapper.EpisodeMapper;
import com.lmt.selfblog.mapper.MarginNoteMapper;
import com.lmt.selfblog.repository.ChapterRepository;
import com.lmt.selfblog.repository.EpisodeRepository;
import com.lmt.selfblog.repository.MarginNoteRepository;
import com.lmt.selfblog.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final ChapterRepository chapterRepository;
    private final MarginNoteRepository marginNoteRepository;

    private final EpisodeMapper episodeMapper;
    private final MarginNoteMapper marginNoteMapper;

    private static final List<ContentStatus> PUBLIC_EPISODE_STATUSES = List.of(ContentStatus.PUBLISHED);
    private static final List<Visibility> PUBLIC_NOTE_VISIBILITIES = List.of(Visibility.PUBLIC);

    @Override
    public AdminEpisodeResponseDTO createEpisode(EpisodeRequestDTO request) {
        Chapter chapter = chapterRepository.findById(request.getChapterId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAPTER_NOT_FOUND, "Chapter not found with ID: " + request.getChapterId()));

        if (episodeRepository.findBySlug(request.getSlug()).isPresent()) {
            throw new ConflictException(ErrorCode.SLUG_ALREADY_EXISTS, "Episode slug already exists: " + request.getSlug());
        }

        Episode episode = episodeMapper.toEntity(request);
        episode.setChapter(chapter);
        Episode saved = episodeRepository.save(episode);
        return episodeMapper.toAdminDto(saved);
    }

    @Override
    public AdminEpisodeResponseDTO updateEpisode(UUID id, EpisodeRequestDTO request) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EPISODE_NOT_FOUND, "Episode not found with ID: " + id));

        Chapter chapter = chapterRepository.findById(request.getChapterId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAPTER_NOT_FOUND, "Chapter not found with ID: " + request.getChapterId()));

        episodeRepository.findBySlug(request.getSlug()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ConflictException(ErrorCode.SLUG_ALREADY_EXISTS, "Episode slug already exists: " + request.getSlug());
            }
        });

        episodeMapper.updateEntity(request, episode);
        episode.setChapter(chapter);
        Episode saved = episodeRepository.save(episode);
        return episodeMapper.toAdminDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminEpisodeResponseDTO getEpisodeById(UUID id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EPISODE_NOT_FOUND, "Episode not found with ID: " + id));
        return episodeMapper.toAdminDto(episode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminEpisodeResponseDTO> getEpisodesByChapterId(UUID chapterId) {
        return episodeRepository.findByChapterIdOrderByOrderIndexAsc(chapterId).stream()
                .map(episodeMapper::toAdminDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEpisode(UUID id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EPISODE_NOT_FOUND, "Episode not found with ID: " + id));
        // Soft delete: status = DELETED
        episode.setStatus(ContentStatus.DELETED);
        episodeRepository.save(episode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicEpisodeResponseDTO> getPublicEpisodesByChapter(String chapterSlug) {
        return episodeRepository.findByChapterSlugAndStatusInOrderByOrderIndexAsc(chapterSlug, PUBLIC_EPISODE_STATUSES)
                .stream()
                .map(episode -> {
                    PublicEpisodeResponseDTO dto = episodeMapper.toPublicDto(episode);
                    Set<PublicMarginNoteResponseDTO> notes = marginNoteRepository
                            .findByEpisodeSlugAndVisibilityIn(episode.getSlug(), PUBLIC_NOTE_VISIBILITIES)
                            .stream()
                            .map(marginNoteMapper::toPublicDto)
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                    dto.setMarginNotes(notes);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PublicEpisodeResponseDTO getPublicEpisodeBySlug(String slug) {
        Episode episode = episodeRepository.findBySlugAndStatusIn(slug, PUBLIC_EPISODE_STATUSES)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EPISODE_NOT_FOUND, "Public Episode not found with slug: " + slug));

        PublicEpisodeResponseDTO dto = episodeMapper.toPublicDto(episode);
        Set<PublicMarginNoteResponseDTO> notes = marginNoteRepository
                .findByEpisodeSlugAndVisibilityIn(slug, PUBLIC_NOTE_VISIBILITIES)
                .stream()
                .map(marginNoteMapper::toPublicDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        dto.setMarginNotes(notes);
        return dto;
    }
}
