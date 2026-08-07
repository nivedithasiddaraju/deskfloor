package com.deskfloor.dto;

public class PayrollSummaryResponse {

    private long totalEmployeesPaid;

    private long totalEmployeesPending;

    private double totalPayrollAmount;

    public PayrollSummaryResponse() {
    }

    public long getTotalEmployeesPaid() {
        return totalEmployeesPaid;
    }

    public void setTotalEmployeesPaid(long totalEmployeesPaid) {
        this.totalEmployeesPaid = totalEmployeesPaid;
    }

    public long getTotalEmployeesPending() {
        return totalEmployeesPending;
    }

    public void setTotalEmployeesPending(long totalEmployeesPending) {
        this.totalEmployeesPending = totalEmployeesPending;
    }

    public double getTotalPayrollAmount() {
        return totalPayrollAmount;
    }

    public void setTotalPayrollAmount(double totalPayrollAmount) {
        this.totalPayrollAmount = totalPayrollAmount;
    }
}