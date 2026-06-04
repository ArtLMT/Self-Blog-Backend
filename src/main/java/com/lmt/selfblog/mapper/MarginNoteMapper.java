package com.lmt.selfblog.mapper;

import com.lmt.selfblog.common.Language;
import com.lmt.selfblog.dto.request.MarginNoteRequestDTO;
import com.lmt.selfblog.dto.response.AdminMarginNoteResponseDTO;
import com.lmt.selfblog.dto.response.PublicMarginNoteResponseDTO;
import com.lmt.selfblog.entity.Episode;
import com.lmt.selfblog.entity.EpisodeTranslation;
import com.lmt.selfblog.entity.MarginNote;
import com.lmt.selfblog.entity.MarginNoteTranslation;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class MarginNoteMapper {

    @Mapping(target = "episodeSlug", source = "episode.slug")
    @Mapping(target = "episodeTitle", expression = "java(getEpisodeTranslation(marginNote.getEpisode(), lang).getTitle())")
    @Mapping(target = "noteContent", expression = "java(getTranslation(marginNote, lang).getNoteContent())")
    @Mapping(target = "language", expression = "java(lang)")
    public abstract PublicMarginNoteResponseDTO toPublicDto(MarginNote marginNote, @Context Language lang);

    @Mapping(target = "episodeId", source = "episode.id")
    @Mapping(target = "episodeTitle", expression = "java(getEpisodeTranslation(marginNote.getEpisode(), lang).getTitle())")
    @Mapping(target = "noteContent", expression = "java(getTranslation(marginNote, lang).getNoteContent())")
    @Mapping(target = "language", expression = "java(lang)")
    public abstract AdminMarginNoteResponseDTO toAdminDto(MarginNote marginNote, @Context Language lang);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "episode", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract MarginNote toEntity(MarginNoteRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "episode", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract void updateEntity(MarginNoteRequestDTO dto, @MappingTarget MarginNote marginNote);

    protected MarginNoteTranslation getTranslation(MarginNote marginNote, Language lang) {
        if (marginNote.getTranslations() == null || marginNote.getTranslations().isEmpty()) {
            return new MarginNoteTranslation();
        }
        return marginNote.getTranslations().stream()
                .filter(t -> t.getLanguage() == lang)
                .findFirst()
                .orElseGet(() -> marginNote.getTranslations().stream().findFirst().orElse(new MarginNoteTranslation()));
    }

    protected EpisodeTranslation getEpisodeTranslation(Episode episode, Language lang) {
        if (episode == null || episode.getTranslations() == null || episode.getTranslations().isEmpty()) {
            return new EpisodeTranslation();
        }
        return episode.getTranslations().stream()
                .filter(t -> t.getLanguage() == lang)
                .findFirst()
                .orElseGet(() -> episode.getTranslations().stream().findFirst().orElse(new EpisodeTranslation()));
    }
}
