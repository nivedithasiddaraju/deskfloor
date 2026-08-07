package com.deskfloor.repository;

import com.deskfloor.entity.Leave;
import com.deskfloor.enums.LeaveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    // Leave History
    List<Leave> findByEmployeeIdOrderByAppliedDateDesc(Long employeeId);

    // Pending / Approved / Rejected
    List<Leave> findByStatusOrderByAppliedDateDesc(LeaveStatus status);

    // Search
    Page<Leave> findByEmployee_FullNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    // Filter
    Page<Leave> findByStatus(
            LeaveStatus status,
            Pageable pageable
    );

    // Dashboard
    long countByStatus(LeaveStatus status);

    long countByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LeaveStatus status,
            LocalDate startDate,
            LocalDate endDate
    );
}