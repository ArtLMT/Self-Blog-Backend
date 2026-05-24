package com.lmt.selfblog.service;

import com.lmt.selfblog.dto.request.ArcRequestDTO;
import com.lmt.selfblog.dto.response.AdminArcResponseDTO;
import com.lmt.selfblog.dto.response.PublicArcResponseDTO;
import com.lmt.selfblog.dto.response.TimelineItemDTO;

import java.util.List;
import java.util.UUID;

public interface ArcService {

    // Admin Operations
    AdminArcResponseDTO createArc(ArcRequestDTO request);

    AdminArcResponseDTO updateArc(UUID id, ArcRequestDTO request);

    AdminArcResponseDTO getArcById(UUID id);

    List<AdminArcResponseDTO> getAllArcsForAdmin();

    void deleteArc(UUID id); // Soft delete / hide

    // Public Operations
    List<PublicArcResponseDTO> getAllPublicArcs();

    PublicArcResponseDTO getPublicArcBySlug(String slug);

    List<TimelineItemDTO> getTimeline();
}
