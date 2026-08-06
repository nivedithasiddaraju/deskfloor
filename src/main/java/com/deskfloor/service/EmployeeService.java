package com.deskfloor.service;

import com.deskfloor.dto.EmployeeRequest;
import com.deskfloor.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse addEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);

}