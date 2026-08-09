package com.deskfloor.service;

import com.deskfloor.dto.AttendanceRequest;
import com.deskfloor.dto.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    AttendanceResponse checkIn(AttendanceRequest request);

    AttendanceResponse checkOut(Long employeeId);

    AttendanceResponse getAttendanceById(Long id);

    List<AttendanceResponse> getAllAttendance();

    List<AttendanceResponse> getTodayAttendance();

    List<AttendanceResponse> getAttendanceByEmployee(Long employeeId);

    List<AttendanceResponse> getAttendanceByDate(LocalDate date);
    List<AttendanceResponse> getMonthlyAttendance(
            Long employeeId,
            int year,
            int month
    );

}