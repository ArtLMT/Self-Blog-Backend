package com.lmt.selfblog.mapper;

import com.lmt.selfblog.common.Language;
import com.lmt.selfblog.dto.request.ChapterRequestDTO;
import com.lmt.selfblog.dto.response.AdminChapterResponseDTO;
import com.lmt.selfblog.dto.response.PublicChapterResponseDTO;
import com.lmt.selfblog.entity.Arc;
import com.lmt.selfblog.entity.ArcTranslation;
import com.lmt.selfblog.entity.Chapter;
import com.lmt.selfblog.entity.ChapterTranslation;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ChapterMapper {

    @Mapping(target = "arcSlug", source = "arc.slug")
    @Mapping(target = "arcTitle", expression = "java(getArcTranslation(chapter.getArc(), lang).getTitle())")
    @Mapping(target = "title", expression = "java(getTranslation(chapter, lang).getTitle())")
    @Mapping(target = "quote", expression = "java(getTranslation(chapter, lang).getQuote())")
    @Mapping(target = "summary", expression = "java(getTranslation(chapter, lang).getSummary())")
    @Mapping(target = "language", expression = "java(lang)")
    public abstract PublicChapterResponseDTO toPublicDto(Chapter chapter, @Context Language lang);

    @Mapping(target = "arcId", source = "arc.id")
    @Mapping(target = "arcTitle", expression = "java(getArcTranslation(chapter.getArc(), lang).getTitle())")
    @Mapping(target = "title", expression = "java(getTranslation(chapter, lang).getTitle())")
    @Mapping(target = "quote", expression = "java(getTranslation(chapter, lang).getQuote())")
    @Mapping(target = "summary", expression = "java(getTranslation(chapter, lang).getSummary())")
    @Mapping(target = "language", expression = "java(lang)")
    public abstract AdminChapterResponseDTO toAdminDto(Chapter chapter, @Context Language lang);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "arc", ignore = true)
    @Mapping(target = "episodes", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract Chapter toEntity(ChapterRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "arc", ignore = true)
    @Mapping(target = "episodes", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract void updateEntity(ChapterRequestDTO dto, @MappingTarget Chapter chapter);

    protected ChapterTranslation getTranslation(Chapter chapter, Language lang) {
        if (chapter.getTranslations() == null || chapter.getTranslations().isEmpty()) {
            return new ChapterTranslation();
        }
        return chapter.getTranslations().stream()
                .filter(t -> t.getLanguage() == lang)
                .findFirst()
                .orElseGet(() -> chapter.getTranslations().stream().findFirst().orElse(new ChapterTranslation()));
    }

    protected ArcTranslation getArcTranslation(Arc arc, Language lang) {
        if (arc == null || arc.getTranslations() == null || arc.getTranslations().isEmpty()) {
            return new ArcTranslation();
        }
        return arc.getTranslations().stream()
                .filter(t -> t.getLanguage() == lang)
                .findFirst()
                .orElseGet(() -> arc.getTranslations().stream().findFirst().orElse(new ArcTranslation()));
    }
}
