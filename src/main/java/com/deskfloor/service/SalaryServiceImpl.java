package com.deskfloor.service;

import com.deskfloor.dto.SalaryRequest;
import com.deskfloor.dto.SalaryResponse;
import com.deskfloor.entity.Employee;
import com.deskfloor.entity.Salary;
import com.deskfloor.enums.SalaryStatus;
import com.deskfloor.exception.ResourceNotFoundException;
import com.deskfloor.repository.EmployeeRepository;
import com.deskfloor.repository.SalaryRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.deskfloor.dto.PayrollSummaryResponse;
import com.deskfloor.dto.MonthlyPayrollResponse;

import java.time.LocalDate;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    public SalaryServiceImpl(SalaryRepository salaryRepository,
                             EmployeeRepository employeeRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public SalaryResponse generateSalary(SalaryRequest request) {

        if (salaryRepository.existsByEmployeeIdAndSalaryMonthAndSalaryYear(
                request.getEmployeeId(),
                request.getSalaryMonth(),
                request.getSalaryYear())) {

            throw new RuntimeException(
                    "Salary already generated for this employee for this month");
        }

        Employee employee = employeeRepository.findById(
                        request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        Salary salary = new Salary();

        salary.setEmployee(employee);
        salary.setBasicSalary(request.getBasicSalary());
        salary.setBonus(request.getBonus());
        salary.setDeduction(request.getDeduction());

        salary.setNetSalary(
                request.getBasicSalary()
                        + request.getBonus()
                        - request.getDeduction()
        );

        salary.setSalaryMonth(request.getSalaryMonth());
        salary.setSalaryYear(request.getSalaryYear());

        salary.setPaymentDate(LocalDate.now());

        salary.setStatus(SalaryStatus.PAID);

        Salary savedSalary = salaryRepository.save(salary);

        return mapToResponse(savedSalary);
    }

    @Override
    public SalaryResponse getSalaryById(Long id) {

        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Salary not found"));

        return mapToResponse(salary);
    }

    @Override
    public Page<SalaryResponse> getAllSalaries(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Salary> salaryPage =
                salaryRepository.findAll(pageable);

        return salaryPage.map(this::mapToResponse);
    }

    @Override
    public SalaryResponse updateSalary(
            Long id,
            SalaryRequest request) {

        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Salary not found"));

        if (salaryRepository
                .existsByEmployeeIdAndSalaryMonthAndSalaryYearAndIdNot(
                        request.getEmployeeId(),
                        request.getSalaryMonth(),
                        request.getSalaryYear(),
                        id)) {

            throw new RuntimeException(
                    "Salary already exists for this employee for this month");
        }

        Employee employee = employeeRepository.findById(
                        request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        salary.setEmployee(employee);

        salary.setBasicSalary(request.getBasicSalary());
        salary.setBonus(request.getBonus());
        salary.setDeduction(request.getDeduction());

        salary.setNetSalary(
                request.getBasicSalary()
                        + request.getBonus()
                        - request.getDeduction()
        );

        salary.setSalaryMonth(request.getSalaryMonth());
        salary.setSalaryYear(request.getSalaryYear());

        Salary updatedSalary = salaryRepository.save(salary);

        return mapToResponse(updatedSalary);
    }
    @Override
    public void deleteSalary(Long id) {

        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Salary not found"));

        salaryRepository.delete(salary);
    }

    @Override
    public Page<SalaryResponse> getEmployeeSalaryHistory(
            Long employeeId,
            int page,
            int size,
            String sortBy,
            String direction) {

        employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Salary> salaryPage =
                salaryRepository.findByEmployeeId(
                        employeeId,
                        pageable
                );

        return salaryPage.map(this::mapToResponse);
    }

    @Override
    public Page<SalaryResponse> searchSalaries(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Salary> salaryPage =
                salaryRepository
                        .findByEmployee_FullNameContainingIgnoreCase(
                                keyword,
                                pageable
                        );

        return salaryPage.map(this::mapToResponse);
    }

    @Override
    public Page<SalaryResponse> filterSalaries(
            SalaryStatus status,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Salary> salaryPage =
                salaryRepository.findByStatus(
                        status,
                        pageable
                );

        return salaryPage.map(this::mapToResponse);
    }
    @Override
    public PayrollSummaryResponse getPayrollSummary() {

        PayrollSummaryResponse response = new PayrollSummaryResponse();

        response.setTotalEmployeesPaid(
                salaryRepository.countByStatus(SalaryStatus.PAID));

        response.setTotalEmployeesPending(
                salaryRepository.countByStatus(SalaryStatus.PENDING));

        Double totalAmount = salaryRepository.getTotalPayrollAmount();

        response.setTotalPayrollAmount(
                totalAmount == null ? 0 : totalAmount);

        return response;
    }
    @Override
    public MonthlyPayrollResponse getMonthlyPayroll(
            String salaryMonth,
            Integer salaryYear) {

        MonthlyPayrollResponse response = new MonthlyPayrollResponse();

        response.setSalaryMonth(salaryMonth);
        response.setSalaryYear(salaryYear);

        response.setTotalEmployees(
                salaryRepository.countBySalaryMonthAndSalaryYear(
                        salaryMonth,
                        salaryYear
                )
        );

        Double totalPayroll =
                salaryRepository.getMonthlyPayrollAmount(
                        salaryMonth,
                        salaryYear
                );

        response.setTotalPayroll(
                totalPayroll == null ? 0 : totalPayroll
        );

        return response;
    }
    @Override
    public Page<SalaryResponse> getEmployeePayrollReport(
            Long employeeId,
            Integer salaryYear,
            int page,
            int size,
            String sortBy,
            String direction) {

        employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Salary> salaryPage =
                salaryRepository.findByEmployeeIdAndSalaryYear(
                        employeeId,
                        salaryYear,
                        pageable
                );

        return salaryPage.map(this::mapToResponse);
    }

    private SalaryResponse mapToResponse(Salary salary) {

        SalaryResponse response = new SalaryResponse();

        response.setId(salary.getId());

        response.setEmployeeId(
                salary.getEmployee().getId());

        response.setEmployeeName(
                salary.getEmployee().getFullName());

        response.setBasicSalary(
                salary.getBasicSalary());

        response.setBonus(
                salary.getBonus());

        response.setDeduction(
                salary.getDeduction());

        response.setNetSalary(
                salary.getNetSalary());

        response.setSalaryMonth(
                salary.getSalaryMonth());

        response.setSalaryYear(
                salary.getSalaryYear());

        response.setPaymentDate(
                salary.getPaymentDate());

        response.setStatus(
                salary.getStatus());

        return response;
    }
}