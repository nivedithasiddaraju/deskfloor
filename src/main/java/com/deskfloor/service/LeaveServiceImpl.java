package com.deskfloor.service;

import com.deskfloor.dto.LeaveRequest;
import com.deskfloor.dto.LeaveResponse;
import com.deskfloor.entity.Employee;
import com.deskfloor.entity.Leave;
import com.deskfloor.enums.LeaveStatus;
import com.deskfloor.exception.ResourceNotFoundException;
import com.deskfloor.repository.EmployeeRepository;
import com.deskfloor.repository.LeaveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveServiceImpl(LeaveRepository leaveRepository,
                            EmployeeRepository employeeRepository) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public LeaveResponse applyLeave(LeaveRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        Leave leave = new Leave();

        leave.setEmployee(employee);
        leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());

        leave.setStatus(LeaveStatus.PENDING);
        leave.setAppliedDate(LocalDate.now());

        Leave savedLeave = leaveRepository.save(leave);

        return mapToResponse(savedLeave);
    }

    @Override
    public LeaveResponse approveLeave(Long leaveId) {

        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave request not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Leave request has already been processed");
        }

        leave.setStatus(LeaveStatus.APPROVED);
        leave.setApprovedDate(LocalDate.now());

        Leave updatedLeave = leaveRepository.save(leave);

        return mapToResponse(updatedLeave);
    }

    @Override
    public LeaveResponse rejectLeave(Long leaveId) {

        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave request not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Leave request has already been processed");
        }

        leave.setStatus(LeaveStatus.REJECTED);
        leave.setApprovedDate(LocalDate.now());

        Leave updatedLeave = leaveRepository.save(leave);

        return mapToResponse(updatedLeave);
    }

    @Override
    public LeaveResponse getLeaveById(Long id) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave request not found"));

        return mapToResponse(leave);
    }

    @Override
    public List<LeaveResponse> getAllLeaves() {

        return leaveRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<LeaveResponse> getEmployeeLeaveHistory(Long employeeId) {

        employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        return leaveRepository
                .findByEmployeeIdOrderByAppliedDateDesc(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<LeaveResponse> getPendingLeaves() {

        return leaveRepository
                .findByStatusOrderByAppliedDateDesc(LeaveStatus.PENDING)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<LeaveResponse> getApprovedLeaves() {

        return leaveRepository
                .findByStatusOrderByAppliedDateDesc(LeaveStatus.APPROVED)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<LeaveResponse> getRejectedLeaves() {

        return leaveRepository
                .findByStatusOrderByAppliedDateDesc(LeaveStatus.REJECTED)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Page<LeaveResponse> searchLeaves(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Leave> leavePage =
                leaveRepository.findByEmployee_FullNameContainingIgnoreCase(
                        keyword,
                        pageable
                );

        return leavePage.map(this::mapToResponse);
    }

    @Override
    public Page<LeaveResponse> filterLeaves(
            LeaveStatus status,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Leave> leavePage =
                leaveRepository.findByStatus(
                        status,
                        pageable
                );

        return leavePage.map(this::mapToResponse);
    }

    private LeaveResponse mapToResponse(Leave leave) {

        LeaveResponse response = new LeaveResponse();

        response.setId(leave.getId());

        response.setEmployeeId(
                leave.getEmployee().getId());

        response.setEmployeeName(
                leave.getEmployee().getFullName());

        response.setLeaveType(
                leave.getLeaveType());

        response.setStartDate(
                leave.getStartDate());

        response.setEndDate(
                leave.getEndDate());

        response.setReason(
                leave.getReason());

        response.setStatus(
                leave.getStatus());

        response.setAppliedDate(
                leave.getAppliedDate());

        response.setApprovedDate(
                leave.getApprovedDate());

        return response;
    }
}