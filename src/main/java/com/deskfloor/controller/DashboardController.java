package com.deskfloor.controller;

import com.deskfloor.dto.ApiResponse;
import com.deskfloor.dto.DashboardResponse;
import com.deskfloor.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deskfloor.dto.EmployeeDashboardResponse;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {

        DashboardResponse response =
                dashboardService.getDashboard();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Dashboard fetched successfully",
                        response
                )
        );
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeDashboardResponse>>
    getEmployeeDashboard(
            @PathVariable Long employeeId) {

        EmployeeDashboardResponse response =
                dashboardService.getEmployeeDashboard(
                        employeeId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee dashboard fetched successfully",
                        response
                )
        );
    }
}