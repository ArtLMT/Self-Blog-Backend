package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.request.ChapterRequestDTO;
import com.lmt.selfblog.dto.response.AdminChapterResponseDTO;
import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.service.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/chapters")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Editorial - Chapters", description = "Admin endpoints for managing narrative Chapters")
public class AdminChapterController {

    private final ChapterService chapterService;

    @PostMapping
    @Operation(summary = "Create a new Chapter")
    public ResponseEntity<ApiResponse<AdminChapterResponseDTO>> createChapter(
            @Valid @RequestBody ChapterRequestDTO request
    ) {
        AdminChapterResponseDTO response = chapterService.createChapter(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Chapter created successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Chapter")
    public ResponseEntity<ApiResponse<AdminChapterResponseDTO>> updateChapter(
            @PathVariable UUID id,
            @Valid @RequestBody ChapterRequestDTO request
    ) {
        AdminChapterResponseDTO response = chapterService.updateChapter(id, request);
        return ResponseEntity.ok(ApiResponse.success("Chapter updated successfully", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Chapter by ID")
    public ResponseEntity<ApiResponse<AdminChapterResponseDTO>> getChapterById(@PathVariable UUID id) {
        AdminChapterResponseDTO response = chapterService.getChapterById(id);
        return ResponseEntity.ok(ApiResponse.success("Chapter retrieved successfully", response));
    }

    @GetMapping("/arc/{arcId}")
    @Operation(summary = "Get Chapters by Arc ID")
    public ResponseEntity<ApiResponse<List<AdminChapterResponseDTO>>> getChaptersByArcId(@PathVariable UUID arcId) {
        List<AdminChapterResponseDTO> response = chapterService.getChaptersByArcId(arcId);
        return ResponseEntity.ok(ApiResponse.success("Chapters retrieved successfully", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete / lock a Chapter")
    public ResponseEntity<ApiResponse<Void>> deleteChapter(@PathVariable UUID id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.ok(ApiResponse.success("Chapter soft deleted/locked successfully", null));
    }
}
