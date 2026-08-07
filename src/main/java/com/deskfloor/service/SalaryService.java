package com.deskfloor.service;

import com.deskfloor.dto.SalaryRequest;
import com.deskfloor.dto.SalaryResponse;
import com.deskfloor.enums.SalaryStatus;
import org.springframework.data.domain.Page;
import com.deskfloor.dto.PayrollSummaryResponse;
import com.deskfloor.dto.MonthlyPayrollResponse;

public interface SalaryService {

    SalaryResponse generateSalary(SalaryRequest request);

    SalaryResponse getSalaryById(Long id);

    Page<SalaryResponse> getAllSalaries(
            int page,
            int size,
            String sortBy,
            String direction
    );

    SalaryResponse updateSalary(
            Long id,
            SalaryRequest request
    );

    void deleteSalary(Long id);

    Page<SalaryResponse> getEmployeeSalaryHistory(
            Long employeeId,
            int page,
            int size,
            String sortBy,
            String direction
    );

    Page<SalaryResponse> searchSalaries(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction
    );

    Page<SalaryResponse> filterSalaries(
            SalaryStatus status,
            int page,
            int size,
            String sortBy,
            String direction
    );
    PayrollSummaryResponse getPayrollSummary();
    MonthlyPayrollResponse getMonthlyPayroll(
            String salaryMonth,
            Integer salaryYear
    );
    Page<SalaryResponse> getEmployeePayrollReport(
            Long employeeId,
            Integer salaryYear,
            int page,
            int size,
            String sortBy,
            String direction
    );
}