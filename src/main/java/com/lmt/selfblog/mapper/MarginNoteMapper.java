package com.lmt.selfblog.mapper;

import com.lmt.selfblog.dto.request.MarginNoteRequestDTO;
import com.lmt.selfblog.dto.response.AdminMarginNoteResponseDTO;
import com.lmt.selfblog.dto.response.PublicMarginNoteResponseDTO;
import com.lmt.selfblog.entity.MarginNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MarginNoteMapper {

    @Mapping(target = "episodeSlug", source = "episode.slug")
    PublicMarginNoteResponseDTO toPublicDto(MarginNote note);

    @Mapping(target = "episodeId", source = "episode.id")
    AdminMarginNoteResponseDTO toAdminDto(MarginNote note);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "episode", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    MarginNote toEntity(MarginNoteRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "episode", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(MarginNoteRequestDTO dto, @MappingTarget MarginNote note);
}
