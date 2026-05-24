package com.lmt.selfblog.mapper;

import com.lmt.selfblog.common.Language;
import com.lmt.selfblog.dto.request.ArcRequestDTO;
import com.lmt.selfblog.dto.response.AdminArcResponseDTO;
import com.lmt.selfblog.dto.response.PublicArcResponseDTO;
import com.lmt.selfblog.entity.Arc;
import com.lmt.selfblog.entity.ArcTranslation;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ChapterMapper.class})
public abstract class ArcMapper {

    @Mapping(target = "title", expression = "java(getTranslation(arc, lang).getTitle())")
    @Mapping(target = "summary", expression = "java(getTranslation(arc, lang).getSummary())")
    @Mapping(target = "language", expression = "java(lang)")
    public abstract PublicArcResponseDTO toPublicDto(Arc arc, @Context Language lang);

    @Mapping(target = "title", expression = "java(getTranslation(arc, lang).getTitle())")
    @Mapping(target = "summary", expression = "java(getTranslation(arc, lang).getSummary())")
    @Mapping(target = "language", expression = "java(lang)")
    public abstract AdminArcResponseDTO toAdminDto(Arc arc, @Context Language lang);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract Arc toEntity(ArcRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract void updateEntity(ArcRequestDTO dto, @MappingTarget Arc arc);

    protected ArcTranslation getTranslation(Arc arc, Language lang) {
        if (arc.getTranslations() == null || arc.getTranslations().isEmpty()) {
            return new ArcTranslation();
        }
        return arc.getTranslations().stream()
                .filter(t -> t.getLanguage() == lang)
                .findFirst()
                .orElseGet(() -> arc.getTranslations().stream().findFirst().orElse(new ArcTranslation()));
    }
}
