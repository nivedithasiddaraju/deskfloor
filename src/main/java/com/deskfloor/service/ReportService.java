package com.deskfloor.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ReportService {

    void exportEmployeesToExcel(
            HttpServletResponse response) throws IOException;

    void exportAttendanceToPdf(
            HttpServletResponse response) throws IOException;

    void exportLeavesToExcel(
            HttpServletResponse response) throws IOException;

    void exportMonthlyReportToPdf(
            int year,
            int month,
            HttpServletResponse response) throws IOException;
}