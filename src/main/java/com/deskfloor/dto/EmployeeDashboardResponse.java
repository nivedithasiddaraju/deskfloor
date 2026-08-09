package com.deskfloor.dto;

import java.util.List;

public class EmployeeDashboardResponse {

    private Long employeeId;
    private String employeeName;

    private AttendanceResponse todayAttendance;

    private Double workingHours;

    private LeaveResponse leaveStatus;

    private List<String> notifications;

    public EmployeeDashboardResponse() {
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public AttendanceResponse getTodayAttendance() {
        return todayAttendance;
    }

    public void setTodayAttendance(
            AttendanceResponse todayAttendance) {
        this.todayAttendance = todayAttendance;
    }

    public Double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Double workingHours) {
        this.workingHours = workingHours;
    }

    public LeaveResponse getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(
            LeaveResponse leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(
            List<String> notifications) {
        this.notifications = notifications;
    }
}