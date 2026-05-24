package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.common.Language;
import com.lmt.selfblog.common.LanguageResolver;
import com.lmt.selfblog.common.Visibility;
import com.lmt.selfblog.dto.request.ArcRequestDTO;
import com.lmt.selfblog.dto.response.*;
import com.lmt.selfblog.entity.Arc;
import com.lmt.selfblog.entity.ArcTranslation;
import com.lmt.selfblog.exception.ConflictException;
import com.lmt.selfblog.exception.NotFoundException;
import com.lmt.selfblog.mapper.ArcMapper;
import com.lmt.selfblog.mapper.ChapterMapper;
import com.lmt.selfblog.mapper.EpisodeMapper;
import com.lmt.selfblog.mapper.MarginNoteMapper;
import com.lmt.selfblog.repository.ArcRepository;
import com.lmt.selfblog.repository.ChapterRepository;
import com.lmt.selfblog.repository.EpisodeRepository;
import com.lmt.selfblog.repository.MarginNoteRepository;
import com.lmt.selfblog.service.ArcService;
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
public class ArcServiceImpl implements ArcService {

    private final ArcRepository arcRepository;
    private final ChapterRepository chapterRepository;
    private final EpisodeRepository episodeRepository;
    private final MarginNoteRepository marginNoteRepository;

    private final ArcMapper arcMapper;
    private final ChapterMapper chapterMapper;
    private final EpisodeMapper episodeMapper;
    private final MarginNoteMapper marginNoteMapper;
    private final LanguageResolver languageResolver;

    private static final List<ContentStatus> PUBLIC_ARC_STATUSES = List.of(ContentStatus.PUBLISHED);
    private static final List<ContentStatus> PUBLIC_CHAPTER_STATUSES = List.of(ContentStatus.PUBLISHED);
    private static final List<ContentStatus> PUBLIC_EPISODE_STATUSES = List.of(ContentStatus.PUBLISHED);
    private static final List<Visibility> PUBLIC_NOTE_VISIBILITIES = List.of(Visibility.PUBLIC);

    @Override
    public AdminArcResponseDTO createArc(ArcRequestDTO request) {
        if (arcRepository.findBySlug(request.getSlug()).isPresent()) {
            throw new ConflictException(ErrorCode.SLUG_ALREADY_EXISTS, "Arc slug already exists: " + request.getSlug());
        }
        Arc arc = arcMapper.toEntity(request);
        
        ArcTranslation translation = new ArcTranslation();
        translation.setLanguage(request.getLanguage());
        translation.setTitle(request.getTitle());
        translation.setSummary(request.getSummary());
        arc.addTranslation(translation);

        Arc saved = arcRepository.save(arc);
        return arcMapper.toAdminDto(saved, request.getLanguage());
    }

    @Override
    public AdminArcResponseDTO updateArc(UUID id, ArcRequestDTO request) {
        Arc arc = arcRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ARC_NOT_FOUND, "Arc not found with ID: " + id));

        arcRepository.findBySlug(request.getSlug()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ConflictException(ErrorCode.SLUG_ALREADY_EXISTS, "Arc slug already exists: " + request.getSlug());
            }
        });

        arcMapper.updateEntity(request, arc);
        
        ArcTranslation translation = arc.getTranslations().stream()
                .filter(t -> t.getLanguage() == request.getLanguage())
                .findFirst()
                .orElseGet(() -> {
                    ArcTranslation newTrans = new ArcTranslation();
                    newTrans.setLanguage(request.getLanguage());
                    arc.addTranslation(newTrans);
                    return newTrans;
                });
        
        translation.setTitle(request.getTitle());
        translation.setSummary(request.getSummary());

        Arc saved = arcRepository.save(arc);
        return arcMapper.toAdminDto(saved, request.getLanguage());
    }

    @Override
    @Transactional(readOnly = true)
    public AdminArcResponseDTO getArcById(UUID id) {
        Arc arc = arcRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ARC_NOT_FOUND, "Arc not found with ID: " + id));
        Language lang = languageResolver.resolveLanguage();
        return arcMapper.toAdminDto(arc, lang);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminArcResponseDTO> getAllArcsForAdmin() {
        Language lang = languageResolver.resolveLanguage();
        return arcRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(arc -> arcMapper.toAdminDto(arc, lang))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteArc(UUID id) {
        Arc arc = arcRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ARC_NOT_FOUND, "Arc not found with ID: " + id));
        arc.setStatus(ContentStatus.DELETED);
        arc.setVisibility(Visibility.PRIVATE);
        arcRepository.save(arc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicArcResponseDTO> getAllPublicArcs() {
        Language lang = languageResolver.resolveLanguage();
        return arcRepository.findByVisibilityAndStatusInOrderByDisplayOrderAsc(Visibility.PUBLIC, PUBLIC_ARC_STATUSES)
                .stream()
                .map(arc -> {
                    PublicArcResponseDTO dto = arcMapper.toPublicDto(arc, lang);
                    Set<PublicChapterResponseDTO> chapters = chapterRepository
                            .findByArcSlugAndStatusInOrderByOrderIndexAsc(arc.getSlug(), PUBLIC_CHAPTER_STATUSES)
                            .stream()
                            .map(chapter -> {
                                PublicChapterResponseDTO cDto = chapterMapper.toPublicDto(chapter, lang);
                                cDto.setEpisodes(buildPublicEpisodes(chapter.getSlug(), lang));
                                return cDto;
                            })
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                    dto.setChapters(chapters);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PublicArcResponseDTO getPublicArcBySlug(String slug) {
        Arc arc = arcRepository.findBySlugAndVisibilityAndStatusIn(slug, Visibility.PUBLIC, PUBLIC_ARC_STATUSES)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ARC_NOT_FOUND, "Public Arc not found with slug: " + slug));

        Language lang = languageResolver.resolveLanguage();
        PublicArcResponseDTO dto = arcMapper.toPublicDto(arc, lang);

        Set<PublicChapterResponseDTO> chapters = chapterRepository
                .findByArcSlugAndStatusInOrderByOrderIndexAsc(slug, PUBLIC_CHAPTER_STATUSES)
                .stream()
                .map(chapter -> {
                    PublicChapterResponseDTO cDto = chapterMapper.toPublicDto(chapter, lang);
                    cDto.setEpisodes(buildPublicEpisodes(chapter.getSlug(), lang));
                    return cDto;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));

        dto.setChapters(chapters);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimelineItemDTO> getTimeline() {
        Language lang = languageResolver.resolveLanguage();
        return arcRepository.findTimeline(lang);
    }

    /*TODO: duplicate code */
    private Set<PublicEpisodeResponseDTO> buildPublicEpisodes(String chapterSlug, Language lang) {
        return episodeRepository
                .findByChapterSlugAndStatusInOrderByOrderIndexAsc(chapterSlug, PUBLIC_EPISODE_STATUSES)
                .stream()
                .map(episode -> {
                    PublicEpisodeResponseDTO eDto = episodeMapper.toPublicDto(episode, lang);
                    Set<PublicMarginNoteResponseDTO> notes = marginNoteRepository
                            .findByEpisodeSlugAndVisibilityIn(episode.getSlug(), PUBLIC_NOTE_VISIBILITIES)
                            .stream()
                            .map(note -> marginNoteMapper.toPublicDto(note, lang))
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                    eDto.setMarginNotes(notes);
                    return eDto;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
