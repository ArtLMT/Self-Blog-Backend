package com.lmt.selfblog.mapper;

import com.lmt.selfblog.common.Language;
import com.lmt.selfblog.dto.request.EpisodeRequestDTO;
import com.lmt.selfblog.dto.response.AdminEpisodeResponseDTO;
import com.lmt.selfblog.dto.response.PublicEpisodeResponseDTO;
import com.lmt.selfblog.entity.Episode;
import com.lmt.selfblog.entity.EpisodeTranslation;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {MarginNoteMapper.class})
public abstract class EpisodeMapper {

    @Mapping(target = "chapterSlug", source = "chapter.slug")
    @Mapping(target = "title", expression = "java(getTranslation(episode, lang).getTitle())")
    @Mapping(target = "markdownContent", expression = "java(getTranslation(episode, lang).getMarkdownContent())")
    @Mapping(target = "renderedContent", expression = "java(getTranslation(episode, lang).getRenderedContent())")
    @Mapping(target = "conclusion", expression = "java(getTranslation(episode, lang).getConclusion())")
    @Mapping(target = "language", expression = "java(lang)")
    public abstract PublicEpisodeResponseDTO toPublicDto(Episode episode, @Context Language lang);

    @Mapping(target = "chapterId", source = "chapter.id")
    @Mapping(target = "title", expression = "java(getTranslation(episode, lang).getTitle())")
    @Mapping(target = "markdownContent", expression = "java(getTranslation(episode, lang).getMarkdownContent())")
    @Mapping(target = "renderedContent", expression = "java(getTranslation(episode, lang).getRenderedContent())")
    @Mapping(target = "conclusion", expression = "java(getTranslation(episode, lang).getConclusion())")
    @Mapping(target = "language", expression = "java(lang)")
    public abstract AdminEpisodeResponseDTO toAdminDto(Episode episode, @Context Language lang);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapter", ignore = true)
    @Mapping(target = "marginNotes", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract Episode toEntity(EpisodeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapter", ignore = true)
    @Mapping(target = "marginNotes", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract void updateEntity(EpisodeRequestDTO dto, @MappingTarget Episode episode);

    protected EpisodeTranslation getTranslation(Episode episode, Language lang) {
        if (episode.getTranslations() == null || episode.getTranslations().isEmpty()) {
            return new EpisodeTranslation();
        }
        return episode.getTranslations().stream()
                .filter(t -> t.getLanguage() == lang)
                .findFirst()
                .orElseGet(() -> episode.getTranslations().stream().findFirst().orElse(new EpisodeTranslation()));
    }
}
