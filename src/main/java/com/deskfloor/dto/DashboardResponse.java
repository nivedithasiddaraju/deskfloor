package com.deskfloor.dto;
import java.util.List;
import com.deskfloor.dto.AttendanceStatisticsResponse;
import com.deskfloor.dto.LeaveStatisticsResponse;

public class DashboardResponse {

    private long totalEmployees;

    private long activeEmployees;

    private long inactiveEmployees;

    private long totalDepartments;

    private long presentToday;

    private long absentToday;

    private long employeesOnLeave;
    private List<DepartmentStatisticsResponse> departmentStatistics;
    private long employeesJoinedToday;

    private long employeesJoinedThisMonth;

    private long employeesJoinedThisYear;
    private AttendanceStatisticsResponse attendanceStatistics;

    private LeaveStatisticsResponse leaveStatistics;
    private EmployeeStatusStatisticsResponse employeeStatusStatistics;
    public DashboardResponse() {
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getActiveEmployees() {
        return activeEmployees;
    }

    public void setActiveEmployees(long activeEmployees) {
        this.activeEmployees = activeEmployees;
    }

    public long getInactiveEmployees() {
        return inactiveEmployees;
    }

    public void setInactiveEmployees(long inactiveEmployees) {
        this.inactiveEmployees = inactiveEmployees;
    }

    public long getTotalDepartments() {
        return totalDepartments;
    }

    public void setTotalDepartments(long totalDepartments) {
        this.totalDepartments = totalDepartments;
    }

    public long getPresentToday() {
        return presentToday;
    }

    public void setPresentToday(long presentToday) {
        this.presentToday = presentToday;
    }

    public long getAbsentToday() {
        return absentToday;
    }

    public void setAbsentToday(long absentToday) {
        this.absentToday = absentToday;
    }

    public long getEmployeesOnLeave() {
        return employeesOnLeave;
    }

    public void setEmployeesOnLeave(long employeesOnLeave) {
        this.employeesOnLeave = employeesOnLeave;
    }

    public List<DepartmentStatisticsResponse> getDepartmentStatistics() {
        return departmentStatistics;
    }

    public void setDepartmentStatistics(
            List<DepartmentStatisticsResponse> departmentStatistics) {
        this.departmentStatistics = departmentStatistics;
    }
    public long getEmployeesJoinedToday() {
        return employeesJoinedToday;
    }

    public void setEmployeesJoinedToday(long employeesJoinedToday) {
        this.employeesJoinedToday = employeesJoinedToday;
    }

    public long getEmployeesJoinedThisMonth() {
        return employeesJoinedThisMonth;
    }

    public void setEmployeesJoinedThisMonth(long employeesJoinedThisMonth) {
        this.employeesJoinedThisMonth = employeesJoinedThisMonth;
    }

    public long getEmployeesJoinedThisYear() {
        return employeesJoinedThisYear;
    }

    public void setEmployeesJoinedThisYear(long employeesJoinedThisYear) {
        this.employeesJoinedThisYear = employeesJoinedThisYear;
    }
    public AttendanceStatisticsResponse getAttendanceStatistics() {
        return attendanceStatistics;
    }

    public void setAttendanceStatistics(
            AttendanceStatisticsResponse attendanceStatistics) {
        this.attendanceStatistics = attendanceStatistics;
    }

    public LeaveStatisticsResponse getLeaveStatistics() {
        return leaveStatistics;
    }

    public void setLeaveStatistics(
            LeaveStatisticsResponse leaveStatistics) {
        this.leaveStatistics = leaveStatistics;
    }
    public EmployeeStatusStatisticsResponse getEmployeeStatusStatistics() {
        return employeeStatusStatistics;
    }

    public void setEmployeeStatusStatistics(
            EmployeeStatusStatisticsResponse employeeStatusStatistics) {
        this.employeeStatusStatistics = employeeStatusStatistics;
    }
}