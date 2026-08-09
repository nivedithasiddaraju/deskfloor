package com.deskfloor.service;

import com.deskfloor.dto.DashboardResponse;
import com.deskfloor.dto.EmployeeDashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard();
    EmployeeDashboardResponse getEmployeeDashboard(
            Long employeeId
    );
}