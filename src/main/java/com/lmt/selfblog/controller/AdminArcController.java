package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.request.ArcRequestDTO;
import com.lmt.selfblog.dto.response.AdminArcResponseDTO;
import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.service.ArcService;
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
@RequestMapping("/api/admin/arcs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Editorial - Arcs", description = "Admin endpoints for managing narrative Arcs")
public class AdminArcController {

    private final ArcService arcService;

    @PostMapping
    @Operation(summary = "Create a new Arc")
    public ResponseEntity<ApiResponse<AdminArcResponseDTO>> createArc(
            @Valid @RequestBody ArcRequestDTO request
    ) {
        AdminArcResponseDTO response = arcService.createArc(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Arc created successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Arc")
    public ResponseEntity<ApiResponse<AdminArcResponseDTO>> updateArc(
            @PathVariable UUID id,
            @Valid @RequestBody ArcRequestDTO request
    ) {
        AdminArcResponseDTO response = arcService.updateArc(id, request);
        return ResponseEntity.ok(ApiResponse.success("Arc updated successfully", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Arc by ID")
    public ResponseEntity<ApiResponse<AdminArcResponseDTO>> getArcById(@PathVariable UUID id) {
        AdminArcResponseDTO response = arcService.getArcById(id);
        return ResponseEntity.ok(ApiResponse.success("Arc retrieved successfully", response));
    }

    @GetMapping
    @Operation(summary = "Get all Arcs for editorial management")
    public ResponseEntity<ApiResponse<List<AdminArcResponseDTO>>> getAllArcs() {
        List<AdminArcResponseDTO> response = arcService.getAllArcsForAdmin();
        return ResponseEntity.ok(ApiResponse.success("All Arcs retrieved successfully", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete / hide an Arc")
    public ResponseEntity<ApiResponse<Void>> deleteArc(@PathVariable UUID id) {
        arcService.deleteArc(id);
        return ResponseEntity.ok(ApiResponse.success("Arc soft deleted successfully", null));
    }
}
