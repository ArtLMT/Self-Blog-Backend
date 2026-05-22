package com.lmt.selfblog.mapper;

import com.lmt.selfblog.dto.request.EpisodeRequestDTO;
import com.lmt.selfblog.dto.response.AdminEpisodeResponseDTO;
import com.lmt.selfblog.dto.response.PublicEpisodeResponseDTO;
import com.lmt.selfblog.entity.Episode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {MarginNoteMapper.class})
public interface EpisodeMapper {

    @Mapping(target = "chapterSlug", source = "chapter.slug")
    PublicEpisodeResponseDTO toPublicDto(Episode episode);

    @Mapping(target = "chapterId", source = "chapter.id")
    AdminEpisodeResponseDTO toAdminDto(Episode episode);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapter", ignore = true)
    @Mapping(target = "marginNotes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Episode toEntity(EpisodeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapter", ignore = true)
    @Mapping(target = "marginNotes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(EpisodeRequestDTO dto, @MappingTarget Episode episode);
}
