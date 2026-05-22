package com.lmt.selfblog.mapper;

import com.lmt.selfblog.dto.request.ArcRequestDTO;
import com.lmt.selfblog.dto.response.AdminArcResponseDTO;
import com.lmt.selfblog.dto.response.PublicArcResponseDTO;
import com.lmt.selfblog.entity.Arc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ChapterMapper.class})
public interface ArcMapper {

    PublicArcResponseDTO toPublicDto(Arc arc);

    AdminArcResponseDTO toAdminDto(Arc arc);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Arc toEntity(ArcRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(ArcRequestDTO dto, @MappingTarget Arc arc);
}
