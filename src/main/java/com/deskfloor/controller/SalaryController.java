package com.deskfloor.controller;

import com.deskfloor.dto.ApiResponse;
import com.deskfloor.dto.SalaryRequest;
import com.deskfloor.dto.SalaryResponse;
import com.deskfloor.enums.SalaryStatus;
import com.deskfloor.service.SalaryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.deskfloor.dto.PayrollSummaryResponse;
import com.deskfloor.dto.MonthlyPayrollResponse;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SalaryResponse>> generateSalary(
            @Valid @RequestBody SalaryRequest request) {

        SalaryResponse response =
                salaryService.generateSalary(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Salary generated successfully",
                        response
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SalaryResponse>> getSalaryById(
            @PathVariable Long id) {

        SalaryResponse response =
                salaryService.getSalaryById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Salary fetched successfully",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SalaryResponse>>> getAllSalaries(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        Page<SalaryResponse> response =
                salaryService.getAllSalaries(
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Salaries fetched successfully",
                        response
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SalaryResponse>> updateSalary(

            @PathVariable Long id,

            @Valid @RequestBody SalaryRequest request) {

        SalaryResponse response =
                salaryService.updateSalary(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Salary updated successfully",
                        response
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSalary(
            @PathVariable Long id) {

        salaryService.deleteSalary(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Salary deleted successfully",
                        "Deleted"
                )
        );
    }

    @GetMapping("/history/{employeeId}")
    public ResponseEntity<ApiResponse<Page<SalaryResponse>>> getEmployeeSalaryHistory(

            @PathVariable Long employeeId,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        Page<SalaryResponse> response =
                salaryService.getEmployeeSalaryHistory(
                        employeeId,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Salary history fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<SalaryResponse>>> searchSalaries(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        Page<SalaryResponse> response =
                salaryService.searchSalaries(
                        keyword,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Salaries fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<SalaryResponse>>> filterSalaries(

            @RequestParam SalaryStatus status,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        Page<SalaryResponse> response =
                salaryService.filterSalaries(
                        status,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Salaries fetched successfully",
                        response
                )
        );
    }
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<PayrollSummaryResponse>> getPayrollSummary() {

        PayrollSummaryResponse response =
                salaryService.getPayrollSummary();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payroll summary fetched successfully",
                        response
                )
        );
    }
    @GetMapping("/monthly-report")
    public ResponseEntity<ApiResponse<MonthlyPayrollResponse>> getMonthlyPayroll(

            @RequestParam String salaryMonth,

            @RequestParam Integer salaryYear) {

        MonthlyPayrollResponse response =
                salaryService.getMonthlyPayroll(
                        salaryMonth,
                        salaryYear
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Monthly payroll report fetched successfully",
                        response
                )
        );
    }
    @GetMapping("/employee-report")
    public ResponseEntity<ApiResponse<Page<SalaryResponse>>> getEmployeePayrollReport(

            @RequestParam Long employeeId,

            @RequestParam Integer salaryYear,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        Page<SalaryResponse> response =
                salaryService.getEmployeePayrollReport(
                        employeeId,
                        salaryYear,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee payroll report fetched successfully",
                        response
                )
        );
    }
}