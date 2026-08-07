package com.deskfloor.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SalaryRequest {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Basic Salary is required")
    @Min(value = 0, message = "Basic Salary must be greater than or equal to 0")
    private Double basicSalary;

    @NotNull(message = "Bonus is required")
    @Min(value = 0, message = "Bonus must be greater than or equal to 0")
    private Double bonus;

    @NotNull(message = "Deduction is required")
    @Min(value = 0, message = "Deduction must be greater than or equal to 0")
    private Double deduction;

    @NotBlank(message = "Salary Month is required")
    private String salaryMonth;

    @NotNull(message = "Salary Year is required")
    private Integer salaryYear;

    public SalaryRequest() {
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    public String getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(String salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public Integer getSalaryYear() {
        return salaryYear;
    }

    public void setSalaryYear(Integer salaryYear) {
        this.salaryYear = salaryYear;
    }
}