package com.deskfloor.service;

import com.deskfloor.dto.LeaveRequest;
import com.deskfloor.dto.LeaveResponse;
import com.deskfloor.enums.LeaveStatus;
import org.springframework.data.domain.Page;
import com.deskfloor.dto.LeaveBalanceResponse;

import java.util.List;

public interface LeaveService {

    LeaveResponse applyLeave(LeaveRequest request);

    LeaveResponse approveLeave(Long leaveId);

    LeaveResponse rejectLeave(Long leaveId);

    LeaveResponse getLeaveById(Long id);

    List<LeaveResponse> getAllLeaves();

    List<LeaveResponse> getEmployeeLeaveHistory(Long employeeId);

    List<LeaveResponse> getPendingLeaves();

    List<LeaveResponse> getApprovedLeaves();

    List<LeaveResponse> getRejectedLeaves();
    List<LeaveBalanceResponse> getLeaveBalance(Long employeeId);

    Page<LeaveResponse> searchLeaves(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction
    );

    Page<LeaveResponse> filterLeaves(
            LeaveStatus status,
            int page,
            int size,
            String sortBy,
            String direction
    );
}