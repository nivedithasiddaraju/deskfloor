package com.deskfloor.service;

import com.deskfloor.dto.EmployeeRequest;
import com.deskfloor.dto.EmployeeResponse;
import com.deskfloor.enums.EmployeeStatus;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    EmployeeResponse addEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    Page<EmployeeResponse> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String direction
    );

    Page<EmployeeResponse> searchEmployees(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction
    );

    Page<EmployeeResponse> filterEmployees(
            String department,
            EmployeeStatus status,
            int page,
            int size,
            String sortBy,
            String direction
    );

    EmployeeResponse updateEmployee(Long id,
                                    EmployeeRequest request);

    void deleteEmployee(Long id);

}