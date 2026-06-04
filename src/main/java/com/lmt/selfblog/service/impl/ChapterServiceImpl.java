package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.common.Language;
import com.lmt.selfblog.common.LanguageResolver;
import com.lmt.selfblog.common.Visibility;
import com.lmt.selfblog.dto.request.ChapterRequestDTO;
import com.lmt.selfblog.dto.response.AdminChapterResponseDTO;
import com.lmt.selfblog.dto.response.PublicChapterResponseDTO;
import com.lmt.selfblog.dto.response.PublicEpisodeResponseDTO;
import com.lmt.selfblog.dto.response.PublicMarginNoteResponseDTO;
import com.lmt.selfblog.entity.Arc;
import com.lmt.selfblog.entity.Chapter;
import com.lmt.selfblog.entity.ChapterTranslation;
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
    private final LanguageResolver languageResolver;

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
        
        ChapterTranslation translation = new ChapterTranslation();
        translation.setLanguage(request.getLanguage());
        translation.setTitle(request.getTitle());
        translation.setQuote(request.getQuote());
        translation.setSummary(request.getSummary());
        chapter.addTranslation(translation);

        Chapter saved = chapterRepository.save(chapter);
        return chapterMapper.toAdminDto(saved, request.getLanguage());
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
        
        ChapterTranslation translation = chapter.getTranslations().stream()
                .filter(t -> t.getLanguage() == request.getLanguage())
                .findFirst()
                .orElseGet(() -> {
                    ChapterTranslation newTrans = new ChapterTranslation();
                    newTrans.setLanguage(request.getLanguage());
                    chapter.addTranslation(newTrans);
                    return newTrans;
                });
        
        translation.setTitle(request.getTitle());
        translation.setQuote(request.getQuote());
        translation.setSummary(request.getSummary());

        Chapter saved = chapterRepository.save(chapter);
        return chapterMapper.toAdminDto(saved, request.getLanguage());
    }

    @Override
    @Transactional(readOnly = true)
    public AdminChapterResponseDTO getChapterById(UUID id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAPTER_NOT_FOUND, "Chapter not found with ID: " + id));
        Language lang = languageResolver.resolveLanguage();
        return chapterMapper.toAdminDto(chapter, lang);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminChapterResponseDTO> getChaptersByArcId(UUID arcId) {
        Language lang = languageResolver.resolveLanguage();
        return chapterRepository.findByArcIdOrderByOrderIndexAsc(arcId).stream()
                .map(c -> chapterMapper.toAdminDto(c, lang))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteChapter(UUID id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAPTER_NOT_FOUND, "Chapter not found with ID: " + id));
        chapter.setStatus(ContentStatus.DELETED);
        chapterRepository.save(chapter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicChapterResponseDTO> getPublicChaptersByArc(String arcSlug) {
        Language lang = languageResolver.resolveLanguage();
        return chapterRepository.findByArcSlugAndStatusInOrderByOrderIndexAsc(arcSlug, PUBLIC_CHAPTER_STATUSES)
                .stream()
                .map(chapter -> chapterMapper.toPublicDto(chapter, lang))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PublicChapterResponseDTO getPublicChapterBySlug(String slug) {
        Chapter chapter = chapterRepository.findBySlugAndStatusIn(slug, PUBLIC_CHAPTER_STATUSES)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAPTER_NOT_FOUND, "Public Chapter not found with slug: " + slug));

        Language lang = languageResolver.resolveLanguage();
        return chapterMapper.toPublicDto(chapter, lang);
    }

    @Override
    @Transactional(readOnly = true)
    public List<com.lmt.selfblog.dto.response.ChaptersIndexArcDTO> getPublicChaptersIndex() {
        Language lang = languageResolver.resolveLanguage();
        
        List<Arc> publicArcs = arcRepository.findByVisibilityAndStatusInOrderByDisplayOrderAsc(
                Visibility.PUBLIC, 
                List.of(ContentStatus.PUBLISHED)
        );

        return publicArcs.stream().map(arc -> {
            com.lmt.selfblog.dto.response.ChaptersIndexArcDTO arcDto = new com.lmt.selfblog.dto.response.ChaptersIndexArcDTO();
            arcDto.setSlug(arc.getSlug());
            
            com.lmt.selfblog.entity.ArcTranslation arcTrans = arc.getTranslations().stream()
                    .filter(t -> t.getLanguage() == lang)
                    .findFirst()
                    .orElseGet(() -> arc.getTranslations().stream().findFirst().orElse(null));
                    
            if (arcTrans != null) {
                arcDto.setTitle(arcTrans.getTitle());
            }

            List<com.lmt.selfblog.dto.response.ChaptersIndexChapterDTO> chapters = chapterRepository
                    .findByArcSlugAndStatusInOrderByOrderIndexAsc(arc.getSlug(), PUBLIC_CHAPTER_STATUSES)
                    .stream()
                    .map(chapter -> {
                        com.lmt.selfblog.dto.response.ChaptersIndexChapterDTO chapterDto = new com.lmt.selfblog.dto.response.ChaptersIndexChapterDTO();
                        chapterDto.setSlug(chapter.getSlug());
                        chapterDto.setOrderIndex(chapter.getOrderIndex());
                        chapterDto.setReadingTimeMinutes(chapter.getReadingTimeMinutes());

                        com.lmt.selfblog.entity.ChapterTranslation chapterTrans = chapter.getTranslations().stream()
                                .filter(t -> t.getLanguage() == lang)
                                .findFirst()
                                .orElseGet(() -> chapter.getTranslations().stream().findFirst().orElse(null));

                        if (chapterTrans != null) {
                            chapterDto.setTitle(chapterTrans.getTitle());
                        }

                        return chapterDto;
                    })
                    .collect(Collectors.toList());

            arcDto.setChapters(chapters);
            return arcDto;
        }).collect(Collectors.toList());
    }

}
