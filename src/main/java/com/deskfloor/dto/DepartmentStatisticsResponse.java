package com.deskfloor.dto;

public class DepartmentStatisticsResponse {

    private String departmentName;

    private long employeeCount;

    public DepartmentStatisticsResponse() {
    }

    public DepartmentStatisticsResponse(String departmentName,
                                        long employeeCount) {
        this.departmentName = departmentName;
        this.employeeCount = employeeCount;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(long employeeCount) {
        this.employeeCount = employeeCount;
    }
}