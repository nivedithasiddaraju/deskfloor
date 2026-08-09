package com.deskfloor.service;

import com.deskfloor.dto.AttendanceStatisticsResponse;
import com.deskfloor.dto.DashboardResponse;
import com.deskfloor.dto.DepartmentStatisticsResponse;
import com.deskfloor.dto.EmployeeStatusStatisticsResponse;
import com.deskfloor.dto.LeaveStatisticsResponse;
import com.deskfloor.enums.AttendanceStatus;
import com.deskfloor.enums.EmployeeStatus;
import com.deskfloor.enums.LeaveStatus;
import com.deskfloor.repository.AttendanceRepository;
import com.deskfloor.repository.DepartmentRepository;
import com.deskfloor.repository.EmployeeRepository;
import com.deskfloor.repository.LeaveRepository;
import org.springframework.stereotype.Service;
import com.deskfloor.dto.EmployeeDashboardResponse;
import com.deskfloor.dto.AttendanceResponse;
import com.deskfloor.dto.LeaveResponse;
import com.deskfloor.entity.Attendance;
import com.deskfloor.entity.Leave;
import com.deskfloor.enums.LeaveStatus;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRepository leaveRepository;

    public DashboardServiceImpl(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            AttendanceRepository attendanceRepository,
            LeaveRepository leaveRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.attendanceRepository = attendanceRepository;
        this.leaveRepository = leaveRepository;
    }

    @Override
    public DashboardResponse getDashboard() {

        DashboardResponse response = new DashboardResponse();

        LocalDate today = LocalDate.now();

        // Employee Statistics
        long totalEmployees = employeeRepository.count();

        long activeEmployees =
                employeeRepository.countByStatus(EmployeeStatus.ACTIVE);

        long inactiveEmployees =
                employeeRepository.countByStatus(EmployeeStatus.INACTIVE);

        // Department Statistics
        long totalDepartments = departmentRepository.count();

        List<Object[]> statistics =
                employeeRepository.getDepartmentStatistics();

        List<DepartmentStatisticsResponse> departmentStatistics =
                new ArrayList<>();

        for (Object[] row : statistics) {

            DepartmentStatisticsResponse dto =
                    new DepartmentStatisticsResponse();

            dto.setDepartmentName((String) row[0]);
            dto.setEmployeeCount((Long) row[1]);

            departmentStatistics.add(dto);
        }

        // Attendance Statistics
        long presentToday =
                attendanceRepository.countByAttendanceDateAndStatus(
                        today,
                        AttendanceStatus.PRESENT);

        long totalAttendanceToday =
                attendanceRepository.countByAttendanceDate(today);

        long absentToday =
                totalEmployees - totalAttendanceToday;

        long employeesOnLeave =
                leaveRepository
                        .countByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                                LeaveStatus.APPROVED,
                                today,
                                today);

        AttendanceStatisticsResponse attendanceStatistics =
                new AttendanceStatisticsResponse();

        attendanceStatistics.setPresent(presentToday);
        attendanceStatistics.setAbsent(absentToday);
        attendanceStatistics.setOnLeave(employeesOnLeave);

        // Leave Statistics
        LeaveStatisticsResponse leaveStatistics =
                new LeaveStatisticsResponse();

        leaveStatistics.setPending(
                leaveRepository.countByStatus(LeaveStatus.PENDING));

        leaveStatistics.setApproved(
                leaveRepository.countByStatus(LeaveStatus.APPROVED));

        leaveStatistics.setRejected(
                leaveRepository.countByStatus(LeaveStatus.REJECTED));

        // Hiring Statistics
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfYear = today.withDayOfYear(1);

        long employeesJoinedToday =
                employeeRepository.countByJoiningDate(today);

        long employeesJoinedThisMonth =
                employeeRepository.countByJoiningDateBetween(
                        firstDayOfMonth,
                        today);

        long employeesJoinedThisYear =
                employeeRepository.countByJoiningDateBetween(
                        firstDayOfYear,
                        today);

        // Employee Status Statistics
        EmployeeStatusStatisticsResponse employeeStatusStatistics =
                new EmployeeStatusStatisticsResponse();

        employeeStatusStatistics.setActive(activeEmployees);
        employeeStatusStatistics.setInactive(inactiveEmployees);

        // Set Response
        response.setTotalEmployees(totalEmployees);
        response.setActiveEmployees(activeEmployees);
        response.setInactiveEmployees(inactiveEmployees);

        response.setEmployeesJoinedToday(employeesJoinedToday);
        response.setEmployeesJoinedThisMonth(employeesJoinedThisMonth);
        response.setEmployeesJoinedThisYear(employeesJoinedThisYear);

        response.setTotalDepartments(totalDepartments);

        response.setPresentToday(presentToday);
        response.setAbsentToday(absentToday);
        response.setEmployeesOnLeave(employeesOnLeave);

        response.setDepartmentStatistics(departmentStatistics);

        response.setAttendanceStatistics(attendanceStatistics);

        response.setLeaveStatistics(leaveStatistics);

        response.setEmployeeStatusStatistics(employeeStatusStatistics);

        return response;
    }
    @Override
    public EmployeeDashboardResponse getEmployeeDashboard(
            Long employeeId) {

        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        EmployeeDashboardResponse response =
                new EmployeeDashboardResponse();

        response.setEmployeeId(employee.getId());
        response.setEmployeeName(employee.getFullName());

        // Today's Attendance
        Attendance attendance =
                attendanceRepository
                        .findByEmployeeIdAndAttendanceDate(
                                employeeId,
                                today)
                        .orElse(null);

        if (attendance != null) {

            AttendanceResponse attendanceResponse =
                    new AttendanceResponse();

            attendanceResponse.setId(attendance.getId());
            attendanceResponse.setEmployeeId(
                    employee.getId());
            attendanceResponse.setEmployeeName(
                    employee.getFullName());
            attendanceResponse.setAttendanceDate(
                    attendance.getAttendanceDate());
            attendanceResponse.setCheckInTime(
                    attendance.getCheckInTime());
            attendanceResponse.setCheckOutTime(
                    attendance.getCheckOutTime());
            attendanceResponse.setWorkingHours(
                    attendance.getWorkingHours());
            attendanceResponse.setStatus(
                    attendance.getStatus());

            response.setTodayAttendance(
                    attendanceResponse);

            response.setWorkingHours(
                    attendance.getWorkingHours());

        } else {
            response.setTodayAttendance(null);
            response.setWorkingHours(0.0);
        }

        // Leave Status
        List<Leave> leaves =
                leaveRepository
                        .findByEmployeeIdOrderByAppliedDateDesc(
                                employeeId);

        LeaveResponse currentLeaveResponse = null;

        for (Leave leave : leaves) {

            boolean currentLeave =
                    !today.isBefore(leave.getStartDate())
                            && !today.isAfter(leave.getEndDate());

            if (currentLeave) {

                currentLeaveResponse =
                        new LeaveResponse();

                currentLeaveResponse.setId(
                        leave.getId());

                currentLeaveResponse.setEmployeeId(
                        employee.getId());

                currentLeaveResponse.setEmployeeName(
                        employee.getFullName());

                currentLeaveResponse.setLeaveType(
                        leave.getLeaveType());

                currentLeaveResponse.setStartDate(
                        leave.getStartDate());

                currentLeaveResponse.setEndDate(
                        leave.getEndDate());

                currentLeaveResponse.setReason(
                        leave.getReason());

                currentLeaveResponse.setStatus(
                        leave.getStatus());

                currentLeaveResponse.setAppliedDate(
                        leave.getAppliedDate());

                currentLeaveResponse.setApprovedDate(
                        leave.getApprovedDate());

                break;
            }
        }

        response.setLeaveStatus(currentLeaveResponse);

        // Notifications
        List<String> notifications =
                new ArrayList<>();

        if (employee.getDateOfBirth() != null
                && employee.getDateOfBirth()
                .getMonthValue()
                == today.getMonthValue()
                && employee.getDateOfBirth()
                .getDayOfMonth()
                == today.getDayOfMonth()) {

            notifications.add(
                    "Happy Birthday! 🎉"
            );
        }

        if (employee.getJoiningDate() != null
                && employee.getJoiningDate()
                .getMonthValue()
                == today.getMonthValue()
                && employee.getJoiningDate()
                .getDayOfMonth()
                == today.getDayOfMonth()) {

            int years =
                    today.getYear()
                            - employee.getJoiningDate()
                            .getYear();

            notifications.add(
                    "Happy Work Anniversary! "
                            + years
                            + " year(s) completed."
            );
        }

        for (Leave leave : leaves) {

            if (leave.getStatus() == LeaveStatus.APPROVED
                    && leave.getApprovedDate() != null
                    && leave.getApprovedDate()
                    .equals(today)) {

                notifications.add(
                        "Your "
                                + leave.getLeaveType()
                                + " leave has been approved."
                );
            }

            if (leave.getStatus() == LeaveStatus.REJECTED
                    && leave.getApprovedDate() != null
                    && leave.getApprovedDate()
                    .equals(today)) {

                notifications.add(
                        "Your "
                                + leave.getLeaveType()
                                + " leave has been rejected."
                );
            }
        }

        response.setNotifications(notifications);

        return response;
    }
}