package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.dto.response.PublicArcResponseDTO;
import com.lmt.selfblog.dto.response.TimelineItemDTO;
import com.lmt.selfblog.service.ArcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/arcs")
@RequiredArgsConstructor
@Tag(name = "Public Narrative - Arcs", description = "Public read-only endpoints for reading narrative Arcs")
public class PublicArcController {

    private final ArcService arcService;

    @GetMapping
    @Operation(summary = "Get all public Arcs")
    public ResponseEntity<ApiResponse<List<PublicArcResponseDTO>>> getAllPublicArcs() {
        List<PublicArcResponseDTO> response = arcService.getAllPublicArcs();
        return ResponseEntity.ok(ApiResponse.success("Public Arcs retrieved successfully", response));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Get a public Arc by slug")
    public ResponseEntity<ApiResponse<PublicArcResponseDTO>> getPublicArcBySlug(@PathVariable String slug) {
        PublicArcResponseDTO response = arcService.getPublicArcBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Public Arc retrieved successfully", response));
    }

    @GetMapping("/timeline")
    @Operation(summary = "Get full content timeline as table of contents",
               description = "Returns a flat list of Arc→Chapter→Episode paths for all published content, ordered by display/index order. Intended for the frontend to group into a tree structure.")
    public ResponseEntity<ApiResponse<List<TimelineItemDTO>>> getTimeline() {
        List<TimelineItemDTO> timeline = arcService.getTimeline();
        return ResponseEntity.ok(ApiResponse.success("Timeline retrieved successfully", timeline));
    }
}
