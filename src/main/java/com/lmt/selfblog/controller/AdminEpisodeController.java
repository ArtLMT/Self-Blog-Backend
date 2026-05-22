package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.request.EpisodeRequestDTO;
import com.lmt.selfblog.dto.response.AdminEpisodeResponseDTO;
import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.service.EpisodeService;
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
@RequestMapping("/api/admin/episodes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Editorial - Episodes", description = "Admin endpoints for managing narrative Episodes")
public class AdminEpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    @Operation(summary = "Create a new Episode")
    public ResponseEntity<ApiResponse<AdminEpisodeResponseDTO>> createEpisode(
            @Valid @RequestBody EpisodeRequestDTO request
    ) {
        AdminEpisodeResponseDTO response = episodeService.createEpisode(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Episode created successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Episode")
    public ResponseEntity<ApiResponse<AdminEpisodeResponseDTO>> updateEpisode(
            @PathVariable UUID id,
            @Valid @RequestBody EpisodeRequestDTO request
    ) {
        AdminEpisodeResponseDTO response = episodeService.updateEpisode(id, request);
        return ResponseEntity.ok(ApiResponse.success("Episode updated successfully", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Episode by ID")
    public ResponseEntity<ApiResponse<AdminEpisodeResponseDTO>> getEpisodeById(@PathVariable UUID id) {
        AdminEpisodeResponseDTO response = episodeService.getEpisodeById(id);
        return ResponseEntity.ok(ApiResponse.success("Episode retrieved successfully", response));
    }

    @GetMapping("/chapter/{chapterId}")
    @Operation(summary = "Get Episodes by Chapter ID")
    public ResponseEntity<ApiResponse<List<AdminEpisodeResponseDTO>>> getEpisodesByChapterId(@PathVariable UUID chapterId) {
        List<AdminEpisodeResponseDTO> response = episodeService.getEpisodesByChapterId(chapterId);
        return ResponseEntity.ok(ApiResponse.success("Episodes retrieved successfully", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete an Episode")
    public ResponseEntity<ApiResponse<Void>> deleteEpisode(@PathVariable UUID id) {
        episodeService.deleteEpisode(id);
        return ResponseEntity.ok(ApiResponse.success("Episode soft deleted successfully", null));
    }
}
