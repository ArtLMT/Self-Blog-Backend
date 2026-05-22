package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.request.MarginNoteRequestDTO;
import com.lmt.selfblog.dto.response.AdminMarginNoteResponseDTO;
import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.service.MarginNoteService;
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
@RequestMapping("/api/admin/margin-notes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Editorial - Margin Notes", description = "Admin endpoints for managing margin notes")
public class AdminMarginNoteController {

    private final MarginNoteService marginNoteService;

    @PostMapping
    @Operation(summary = "Create a new Margin Note")
    public ResponseEntity<ApiResponse<AdminMarginNoteResponseDTO>> createMarginNote(
            @Valid @RequestBody MarginNoteRequestDTO request
    ) {
        AdminMarginNoteResponseDTO response = marginNoteService.createMarginNote(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Margin note created successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Margin Note")
    public ResponseEntity<ApiResponse<AdminMarginNoteResponseDTO>> updateMarginNote(
            @PathVariable UUID id,
            @Valid @RequestBody MarginNoteRequestDTO request
    ) {
        AdminMarginNoteResponseDTO response = marginNoteService.updateMarginNote(id, request);
        return ResponseEntity.ok(ApiResponse.success("Margin note updated successfully", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Margin Note by ID")
    public ResponseEntity<ApiResponse<AdminMarginNoteResponseDTO>> getMarginNoteById(@PathVariable UUID id) {
        AdminMarginNoteResponseDTO response = marginNoteService.getMarginNoteById(id);
        return ResponseEntity.ok(ApiResponse.success("Margin note retrieved successfully", response));
    }

    @GetMapping("/episode/{episodeId}")
    @Operation(summary = "Get Margin Notes by Episode ID")
    public ResponseEntity<ApiResponse<List<AdminMarginNoteResponseDTO>>> getMarginNotesByEpisodeId(
            @PathVariable UUID episodeId
    ) {
        List<AdminMarginNoteResponseDTO> response = marginNoteService.getMarginNotesByEpisodeId(episodeId);
        return ResponseEntity.ok(ApiResponse.success("Margin notes retrieved successfully", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete / hide a Margin Note")
    public ResponseEntity<ApiResponse<Void>> deleteMarginNote(@PathVariable UUID id) {
        marginNoteService.deleteMarginNote(id);
        return ResponseEntity.ok(ApiResponse.success("Margin note soft deleted successfully", null));
    }
}
