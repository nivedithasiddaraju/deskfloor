package com.deskfloor.controller;

import com.deskfloor.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/employees/excel")
    public void exportEmployeesToExcel(
            HttpServletResponse response) throws IOException {

        reportService.exportEmployeesToExcel(response);
    }
    @GetMapping("/attendance/pdf")
    public void exportAttendanceToPdf(
            HttpServletResponse response)
            throws IOException {

        reportService.exportAttendanceToPdf(response);
    }
    @GetMapping("/leaves/excel")
    public void exportLeavesToExcel(
            HttpServletResponse response)
            throws IOException {

        reportService.exportLeavesToExcel(response);
    }
    @GetMapping("/monthly/pdf")
    public void exportMonthlyReportToPdf(
            @RequestParam int year,
            @RequestParam int month,
            HttpServletResponse response)
            throws IOException {

        reportService.exportMonthlyReportToPdf(
                year,
                month,
                response
        );
    }
}