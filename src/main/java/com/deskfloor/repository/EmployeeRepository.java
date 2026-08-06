package com.deskfloor.repository;

import com.deskfloor.entity.Employee;
import com.deskfloor.enums.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findByEmployeeCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentDepartmentNameContainingIgnoreCaseOrDesignationContainingIgnoreCase(
            String employeeCode,
            String fullName,
            String email,
            String departmentName,
            String designation,
            Pageable pageable
    );

    Page<Employee> findByDepartmentDepartmentNameContainingIgnoreCaseAndStatus(
            String departmentName,
            EmployeeStatus status,
            Pageable pageable
    );

}