package com.lmt.selfblog.service;

import com.lmt.selfblog.dto.response.AdminWorkspaceMetricsDTO;

public interface AdminDashboardService {
    AdminWorkspaceMetricsDTO getMetrics();
}
