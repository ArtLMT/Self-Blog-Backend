package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.dto.response.PublicEpisodeResponseDTO;
import com.lmt.selfblog.service.EpisodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/episodes")
@RequiredArgsConstructor
@Tag(name = "Public Narrative - Episodes", description = "Public read-only endpoints for Episodes")
public class PublicEpisodeController {

    private final EpisodeService episodeService;

    @GetMapping("/chapter/{chapterSlug}")
    @Operation(summary = "Get all public Episodes for a given Chapter slug")
    public ResponseEntity<ApiResponse<List<PublicEpisodeResponseDTO>>> getPublicEpisodesByChapter(
            @PathVariable String chapterSlug
    ) {
        List<PublicEpisodeResponseDTO> response = episodeService.getPublicEpisodesByChapter(chapterSlug);
        return ResponseEntity.ok(ApiResponse.success("Public Episodes retrieved successfully", response));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Get a public Episode by slug")
    public ResponseEntity<ApiResponse<PublicEpisodeResponseDTO>> getPublicEpisodeBySlug(@PathVariable String slug) {
        PublicEpisodeResponseDTO response = episodeService.getPublicEpisodeBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Public Episode retrieved successfully", response));
    }
}
