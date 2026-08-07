package com.deskfloor.repository;

import com.deskfloor.entity.Employee;
import com.deskfloor.enums.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmployeeCode(String employeeCode);

    Page<Employee> findByEmployeeCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartment_DepartmentNameContainingIgnoreCaseOrDesignationContainingIgnoreCase(
            String employeeCode,
            String fullName,
            String email,
            String departmentName,
            String designation,
            Pageable pageable
    );

    Page<Employee> findByDepartment_DepartmentNameContainingIgnoreCaseAndStatus(
            String departmentName,
            EmployeeStatus status,
            Pageable pageable
    );

    long countByStatus(EmployeeStatus status);

    // Dashboard - Department Statistics
    @Query("""
            SELECT e.department.departmentName,
                   COUNT(e)
            FROM Employee e
            GROUP BY e.department.departmentName
            ORDER BY e.department.departmentName
            """)
    List<Object[]> getDepartmentStatistics();

    // Dashboard - Hiring Statistics
    long countByJoiningDate(LocalDate joiningDate);

    long countByJoiningDateBetween(LocalDate startDate, LocalDate endDate);
}