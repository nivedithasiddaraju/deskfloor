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
}