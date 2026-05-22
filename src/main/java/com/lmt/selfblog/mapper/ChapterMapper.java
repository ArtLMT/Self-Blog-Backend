package com.lmt.selfblog.mapper;

import com.lmt.selfblog.dto.request.ChapterRequestDTO;
import com.lmt.selfblog.dto.response.AdminChapterResponseDTO;
import com.lmt.selfblog.dto.response.PublicChapterResponseDTO;
import com.lmt.selfblog.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EpisodeMapper.class})
public interface ChapterMapper {

    @Mapping(target = "arcSlug", source = "arc.slug")
    PublicChapterResponseDTO toPublicDto(Chapter chapter);

    @Mapping(target = "arcId", source = "arc.id")
    AdminChapterResponseDTO toAdminDto(Chapter chapter);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "arc", ignore = true)
    @Mapping(target = "episodes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Chapter toEntity(ChapterRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "arc", ignore = true)
    @Mapping(target = "episodes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(ChapterRequestDTO dto, @MappingTarget Chapter chapter);
}
