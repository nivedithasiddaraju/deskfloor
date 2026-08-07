package com.deskfloor.dto;

import jakarta.validation.constraints.NotNull;

public class AttendanceRequest {

    @NotNull(message = "Employee Id is required")
    private Long employeeId;

    public AttendanceRequest() {
    }

    public AttendanceRequest(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}