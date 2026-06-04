package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.dto.response.PublicMarginNoteResponseDTO;
import com.lmt.selfblog.service.MarginNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/margin-notes")
@RequiredArgsConstructor
@Tag(name = "Public Margin Notes", description = "Public endpoints for margin notes")
public class PublicMarginNoteController {

    private final MarginNoteService marginNoteService;

    @GetMapping("/episode/{episodeSlug}")
    @Operation(summary = "Get public margin notes for a specific episode by its slug")
    public ResponseEntity<ApiResponse<List<PublicMarginNoteResponseDTO>>> getMarginNotesByEpisode(
            @PathVariable String episodeSlug
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Margin notes retrieved successfully",
                marginNoteService.getPublicMarginNotesForEpisode(episodeSlug)
        ));
    }
}
