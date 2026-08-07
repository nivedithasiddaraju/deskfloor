package com.deskfloor.service;

import com.deskfloor.dto.AttendanceRequest;
import com.deskfloor.dto.AttendanceResponse;
import com.deskfloor.entity.Attendance;
import com.deskfloor.entity.Employee;
import com.deskfloor.enums.AttendanceStatus;
import com.deskfloor.exception.ResourceNotFoundException;
import com.deskfloor.repository.AttendanceRepository;
import com.deskfloor.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            EmployeeRepository employeeRepository) {

        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public AttendanceResponse checkIn(AttendanceRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        attendanceRepository
                .findByEmployeeIdAndAttendanceDate(
                        request.getEmployeeId(),
                        LocalDate.now())
                .ifPresent(a -> {
                    throw new RuntimeException(
                            "Employee has already checked in today");
                });

        Attendance attendance = new Attendance();

        attendance.setEmployee(employee);
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setCheckInTime(LocalTime.now());
        attendance.setStatus(AttendanceStatus.PRESENT);

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        return mapToResponse(savedAttendance);
    }

    @Override
    public AttendanceResponse checkOut(Long employeeId) {

        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndAttendanceDate(
                        employeeId,
                        LocalDate.now())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Attendance not found"));

        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException(
                    "Employee has already checked out today");
        }

        LocalTime checkOutTime = LocalTime.now();

        attendance.setCheckOutTime(checkOutTime);

        Duration duration = Duration.between(
                attendance.getCheckInTime(),
                checkOutTime);

        double hours = duration.toMinutes() / 60.0;

        attendance.setWorkingHours(hours);

        Attendance updatedAttendance =
                attendanceRepository.save(attendance);

        return mapToResponse(updatedAttendance);
    }

    @Override
    public AttendanceResponse getAttendanceById(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Attendance not found"));

        return mapToResponse(attendance);
    }

    @Override
    public List<AttendanceResponse> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getTodayAttendance() {

        return attendanceRepository
                .findByAttendanceDate(LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getAttendanceByEmployee(Long employeeId) {

        employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found"));

        return attendanceRepository
                .findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getAttendanceByDate(LocalDate date) {

        return attendanceRepository
                .findByAttendanceDate(date)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AttendanceResponse mapToResponse(
            Attendance attendance) {

        AttendanceResponse response =
                new AttendanceResponse();

        response.setId(attendance.getId());

        response.setEmployeeId(
                attendance.getEmployee().getId());

        response.setEmployeeName(
                attendance.getEmployee().getFullName());

        response.setAttendanceDate(
                attendance.getAttendanceDate());

        response.setCheckInTime(
                attendance.getCheckInTime());

        response.setCheckOutTime(
                attendance.getCheckOutTime());

        response.setWorkingHours(
                attendance.getWorkingHours());

        response.setStatus(
                attendance.getStatus());

        return response;
    }
}