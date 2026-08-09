package com.deskfloor.repository;

import com.deskfloor.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployeeIdAndAttendanceDate(
            Long employeeId,
            LocalDate attendanceDate
    );

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    List<Attendance> findByEmployeeId(Long employeeId);
    List<Attendance> findByEmployeeIdAndAttendanceDateBetween(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate
    );

    long countByAttendanceDate(java.time.LocalDate attendanceDate);

    long countByAttendanceDateAndStatus(
            java.time.LocalDate attendanceDate,
            com.deskfloor.enums.AttendanceStatus status
    );
}