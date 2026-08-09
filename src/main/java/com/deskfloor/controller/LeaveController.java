package com.deskfloor.controller;

import com.deskfloor.dto.ApiResponse;
import com.deskfloor.dto.LeaveRequest;
import com.deskfloor.dto.LeaveResponse;
import com.deskfloor.enums.LeaveStatus;
import com.deskfloor.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.deskfloor.dto.LeaveBalanceResponse;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<LeaveResponse>> applyLeave(
            @Valid @RequestBody LeaveRequest request) {

        LeaveResponse response = leaveService.applyLeave(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leave applied successfully",
                        response
                )
        );
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<LeaveResponse>> approveLeave(
            @PathVariable Long id) {

        LeaveResponse response = leaveService.approveLeave(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leave approved successfully",
                        response
                )
        );
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponse<LeaveResponse>> rejectLeave(
            @PathVariable Long id) {

        LeaveResponse response = leaveService.rejectLeave(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leave rejected successfully",
                        response
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveResponse>> getLeaveById(
            @PathVariable Long id) {

        LeaveResponse response = leaveService.getLeaveById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leave fetched successfully",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getAllLeaves() {

        List<LeaveResponse> response = leaveService.getAllLeaves();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leaves fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/history/{employeeId}")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getEmployeeLeaveHistory(
            @PathVariable Long employeeId) {

        List<LeaveResponse> response =
                leaveService.getEmployeeLeaveHistory(employeeId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee leave history fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getPendingLeaves() {

        List<LeaveResponse> response = leaveService.getPendingLeaves();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Pending leave requests fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/approved")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getApprovedLeaves() {

        List<LeaveResponse> response = leaveService.getApprovedLeaves();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Approved leave requests fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/rejected")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getRejectedLeaves() {

        List<LeaveResponse> response = leaveService.getRejectedLeaves();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Rejected leave requests fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<LeaveResponse>>> searchLeaves(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "appliedDate") String sortBy,

            @RequestParam(defaultValue = "desc") String direction) {

        Page<LeaveResponse> response =
                leaveService.searchLeaves(
                        keyword,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leaves fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<LeaveResponse>>> filterLeaves(

            @RequestParam LeaveStatus status,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "appliedDate") String sortBy,

            @RequestParam(defaultValue = "desc") String direction) {

        Page<LeaveResponse> response =
                leaveService.filterLeaves(
                        status,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leaves fetched successfully",
                        response
                )
        );
    }
    @GetMapping("/balance/{employeeId}")
    public ResponseEntity<ApiResponse<List<LeaveBalanceResponse>>>
    getLeaveBalance(
            @PathVariable Long employeeId) {

        List<LeaveBalanceResponse> response =
                leaveService.getLeaveBalance(employeeId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leave balance fetched successfully",
                        response
                )
        );
    }
}