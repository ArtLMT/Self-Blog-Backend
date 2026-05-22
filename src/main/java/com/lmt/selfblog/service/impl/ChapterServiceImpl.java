package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.common.Visibility;
import com.lmt.selfblog.dto.request.ChapterRequestDTO;
import com.lmt.selfblog.dto.response.AdminChapterResponseDTO;
import com.lmt.selfblog.dto.response.PublicChapterResponseDTO;
import com.lmt.selfblog.dto.response.PublicEpisodeResponseDTO;
import com.lmt.selfblog.dto.response.PublicMarginNoteResponseDTO;
import com.lmt.selfblog.entity.Arc;
import com.lmt.selfblog.entity.Chapter;
import com.lmt.selfblog.exception.ConflictException;
import com.lmt.selfblog.exception.NotFoundException;
import com.lmt.selfblog.mapper.ChapterMapper;
import com.lmt.selfblog.mapper.EpisodeMapper;
import com.lmt.selfblog.mapper.MarginNoteMapper;
import com.lmt.selfblog.repository.ArcRepository;
import com.lmt.selfblog.repository.ChapterRepository;
import com.lmt.selfblog.repository.EpisodeRepository;
import com.lmt.selfblog.repository.MarginNoteRepository;
import com.lmt.selfblog.service.ChapterService;
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
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final ArcRepository arcRepository;
    private final EpisodeRepository episodeRepository;
    private final MarginNoteRepository marginNoteRepository;

    private final ChapterMapper chapterMapper;
    private final EpisodeMapper episodeMapper;
    private final MarginNoteMapper marginNoteMapper;

    private static final List<ContentStatus> PUBLIC_CHAPTER_STATUSES = List.of(ContentStatus.PUBLISHED);
    private static final List<ContentStatus> PUBLIC_EPISODE_STATUSES = List.of(ContentStatus.PUBLISHED);
    private static final List<Visibility> PUBLIC_NOTE_VISIBILITIES = List.of(Visibility.PUBLIC);

    @Override
    public AdminChapterResponseDTO createChapter(ChapterRequestDTO request) {
        Arc arc = arcRepository.findById(request.getArcId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ARC_NOT_FOUND, "Arc not found with ID: " + request.getArcId()));

        if (chapterRepository.findBySlug(request.getSlug()).isPresent()) {
            throw new ConflictException(ErrorCode.SLUG_ALREADY_EXISTS, "Chapter slug already exists: " + request.getSlug());
        }

        Chapter chapter = chapterMapper.toEntity(request);
        chapter.setArc(arc);
        Chapter saved = chapterRepository.save(chapter);
        return chapterMapper.toAdminDto(saved);
    }

    @Override
    public AdminChapterResponseDTO updateChapter(UUID id, ChapterRequestDTO request) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAPTER_NOT_FOUND, "Chapter not found with ID: " + id));

        Arc arc = arcRepository.findById(request.getArcId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ARC_NOT_FOUND, "Arc not found with ID: " + request.getArcId()));

        chapterRepository.findBySlug(request.getSlug()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ConflictException(ErrorCode.SLUG_ALREADY_EXISTS, "Chapter slug already exists: " + request.getSlug());
            }
        });

        chapterMapper.updateEntity(request, chapter);
        chapter.setArc(arc);
        Chapter saved = chapterRepository.save(chapter);
        return chapterMapper.toAdminDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminChapterResponseDTO getChapterById(UUID id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAPTER_NOT_FOUND, "Chapter not found with ID: " + id));
        return chapterMapper.toAdminDto(chapter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminChapterResponseDTO> getChaptersByArcId(UUID arcId) {
        return chapterRepository.findByArcIdOrderByOrderIndexAsc(arcId).stream()
                .map(chapterMapper::toAdminDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteChapter(UUID id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAPTER_NOT_FOUND, "Chapter not found with ID: " + id));
        // Soft delete: Mark status as DELETED
        chapter.setStatus(ContentStatus.DELETED);
        chapterRepository.save(chapter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicChapterResponseDTO> getPublicChaptersByArc(String arcSlug) {
        return chapterRepository.findByArcSlugAndStatusInOrderByOrderIndexAsc(arcSlug, PUBLIC_CHAPTER_STATUSES)
                .stream()
                .map(chapter -> {
                    PublicChapterResponseDTO dto = chapterMapper.toPublicDto(chapter);
                    // Filter and map public episodes
                    Set<PublicEpisodeResponseDTO> episodes = episodeRepository
                            .findByChapterSlugAndStatusInOrderByOrderIndexAsc(chapter.getSlug(), PUBLIC_EPISODE_STATUSES)
                            .stream()
                            .map(episode -> {
                                PublicEpisodeResponseDTO eDto = episodeMapper.toPublicDto(episode);
                                // Filter and map public notes
                                Set<PublicMarginNoteResponseDTO> notes = marginNoteRepository
                                        .findByEpisodeSlugAndVisibilityIn(episode.getSlug(), PUBLIC_NOTE_VISIBILITIES)
                                        .stream()
                                        .map(marginNoteMapper::toPublicDto)
                                        .collect(Collectors.toCollection(LinkedHashSet::new));
                                eDto.setMarginNotes(notes);
                                return eDto;
                            })
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                    dto.setEpisodes(episodes);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PublicChapterResponseDTO getPublicChapterBySlug(String slug) {
        Chapter chapter = chapterRepository.findBySlugAndStatusIn(slug, PUBLIC_CHAPTER_STATUSES)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAPTER_NOT_FOUND, "Public Chapter not found with slug: " + slug));

        PublicChapterResponseDTO dto = chapterMapper.toPublicDto(chapter);

        // Filter and map public episodes
        Set<PublicEpisodeResponseDTO> episodes = episodeRepository
                .findByChapterSlugAndStatusInOrderByOrderIndexAsc(slug, PUBLIC_EPISODE_STATUSES)
                .stream()
                .map(episode -> {
                    PublicEpisodeResponseDTO eDto = episodeMapper.toPublicDto(episode);
                    // Filter and map public notes
                    Set<PublicMarginNoteResponseDTO> notes = marginNoteRepository
                            .findByEpisodeSlugAndVisibilityIn(episode.getSlug(), PUBLIC_NOTE_VISIBILITIES)
                            .stream()
                            .map(marginNoteMapper::toPublicDto)
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                    eDto.setMarginNotes(notes);
                    return eDto;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));

        dto.setEpisodes(episodes);
        return dto;
    }
}
