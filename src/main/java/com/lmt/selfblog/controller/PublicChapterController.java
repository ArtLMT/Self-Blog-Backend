package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.dto.response.PublicChapterResponseDTO;
import com.lmt.selfblog.service.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/chapters")
@RequiredArgsConstructor
@Tag(name = "Public Narrative - Chapters", description = "Public read-only endpoints for Chapters")
public class PublicChapterController {

    private final ChapterService chapterService;

    @GetMapping("/arc/{arcSlug}")
    @Operation(summary = "Get all public Chapters for a given Arc slug")
    public ResponseEntity<ApiResponse<List<PublicChapterResponseDTO>>> getPublicChaptersByArc(
            @PathVariable String arcSlug
    ) {
        List<PublicChapterResponseDTO> response = chapterService.getPublicChaptersByArc(arcSlug);
        return ResponseEntity.ok(ApiResponse.success("Public Chapters retrieved successfully", response));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Get a public Chapter by slug")
    public ResponseEntity<ApiResponse<PublicChapterResponseDTO>> getPublicChapterBySlug(@PathVariable String slug) {
        PublicChapterResponseDTO response = chapterService.getPublicChapterBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Public Chapter retrieved successfully", response));
    }

    @GetMapping("/index")
    @Operation(summary = "Get a lightweight index of all public Arcs and their Chapters")
    public ResponseEntity<ApiResponse<List<com.lmt.selfblog.dto.response.ChaptersIndexArcDTO>>> getChaptersIndex() {
        return ResponseEntity.ok(ApiResponse.success(
                "Chapters index retrieved successfully",
                chapterService.getPublicChaptersIndex()
        ));
    }
}
