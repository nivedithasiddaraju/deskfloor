package com.deskfloor.controller;

import com.deskfloor.dto.ApiResponse;
import com.deskfloor.dto.AttendanceRequest;
import com.deskfloor.dto.AttendanceResponse;
import com.deskfloor.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<AttendanceResponse>> checkIn(
            @Valid
            @RequestBody
            AttendanceRequest request) {

        AttendanceResponse response =
                attendanceService.checkIn(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Check-In Successful",
                        response
                )
        );
    }

    @PutMapping("/check-out/{employeeId}")
    public ResponseEntity<ApiResponse<AttendanceResponse>> checkOut(
            @PathVariable Long employeeId) {

        AttendanceResponse response =
                attendanceService.checkOut(employeeId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Check-Out Successful",
                        response
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AttendanceResponse>> getAttendanceById(
            @PathVariable Long id) {

        AttendanceResponse response =
                attendanceService.getAttendanceById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Attendance fetched successfully",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAllAttendance() {

        List<AttendanceResponse> response =
                attendanceService.getAllAttendance();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Attendance fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getTodayAttendance() {

        List<AttendanceResponse> response =
                attendanceService.getTodayAttendance();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Today's attendance fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/history/{employeeId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAttendanceByEmployee(
            @PathVariable Long employeeId) {

        List<AttendanceResponse> response =
                attendanceService.getAttendanceByEmployee(employeeId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Attendance history fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/date")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAttendanceByDate(
            @RequestParam LocalDate date) {

        List<AttendanceResponse> response =
                attendanceService.getAttendanceByDate(date);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Attendance fetched successfully",
                        response
                )
        );
    }
}