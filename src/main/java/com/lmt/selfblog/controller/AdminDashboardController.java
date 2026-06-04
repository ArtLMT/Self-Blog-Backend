package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.response.AdminWorkspaceMetricsDTO;
import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.service.AdminDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/workspace")
@RequiredArgsConstructor
@Tag(name = "Admin Workspace Dashboard", description = "Endpoints for admin dashboard metrics")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/metrics")
    @Operation(summary = "Get workspace metrics and recently edited episodes")
    public ResponseEntity<ApiResponse<AdminWorkspaceMetricsDTO>> getMetrics() {
        return ResponseEntity.ok(ApiResponse.success(
                "Metrics retrieved successfully",
                adminDashboardService.getMetrics()
        ));
    }
}
