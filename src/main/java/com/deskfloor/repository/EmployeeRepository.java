package com.deskfloor.repository;

import com.deskfloor.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeCode(String employeeCode);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartment(String department);

    List<Employee> findByFullNameContainingIgnoreCase(String fullName);

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);

}