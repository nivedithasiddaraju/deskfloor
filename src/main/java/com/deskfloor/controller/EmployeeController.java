package com.deskfloor.controller;

import com.deskfloor.dto.ApiResponse;
import com.deskfloor.dto.EmployeeRequest;
import com.deskfloor.dto.EmployeeResponse;
import com.deskfloor.enums.EmployeeStatus;
import com.deskfloor.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> addEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response = employeeService.addEmployee(request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Employee added successfully", response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(
            @PathVariable Long id) {

        EmployeeResponse response = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Employee fetched successfully", response)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EmployeeResponse>>> getAllEmployees(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        Page<EmployeeResponse> response =
                employeeService.getAllEmployees(
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<EmployeeResponse>>> searchEmployees(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        Page<EmployeeResponse> response =
                employeeService.searchEmployees(
                        keyword,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<EmployeeResponse>>> filterEmployees(

            @RequestParam String department,

            @RequestParam EmployeeStatus status,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        Page<EmployeeResponse> response =
                employeeService.filterEmployees(
                        department,
                        status,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully",
                        response
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response =
                employeeService.updateEmployee(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Employee updated successfully", response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee deleted successfully",
                        "Deleted"
                )
        );
    }
    @PostMapping(
            value = "/{id}/profile-picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<EmployeeResponse>>
    uploadProfilePicture(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        EmployeeResponse response =
                employeeService.uploadProfilePicture(id, file);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Profile picture uploaded successfully",
                        response
                )
        );
    }
}