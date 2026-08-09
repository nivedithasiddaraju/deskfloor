package com.deskfloor.service;

import com.deskfloor.entity.Employee;
import com.deskfloor.repository.EmployeeRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.deskfloor.entity.Attendance;
import com.deskfloor.repository.AttendanceRepository;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.IOException;

import com.deskfloor.entity.Leave;
import com.deskfloor.repository.LeaveRepository;
import java.util.List;

import com.deskfloor.enums.LeaveStatus;
import com.lowagie.text.Font;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.stream.Collectors;


@Service
public class ReportServiceImpl implements ReportService {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRepository leaveRepository;


    public ReportServiceImpl(
            EmployeeRepository employeeRepository,
            AttendanceRepository attendanceRepository,
            LeaveRepository leaveRepository) {

        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.leaveRepository = leaveRepository;
    }

    @Override
    public void exportEmployeesToExcel(
            HttpServletResponse response)
            throws IOException {

        List<Employee> employees =
                employeeRepository.findAll();

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet =
                workbook.createSheet("Employees");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Employee Code");
        header.createCell(2).setCellValue("Full Name");
        header.createCell(3).setCellValue("Email");
        header.createCell(4).setCellValue("Phone");
        header.createCell(5).setCellValue("Department");
        header.createCell(6).setCellValue("Designation");
        header.createCell(7).setCellValue("Salary");
        header.createCell(8).setCellValue("Joining Date");
        header.createCell(9).setCellValue("Date of Birth");
        header.createCell(10).setCellValue("Status");

        int rowNumber = 1;

        for (Employee employee : employees) {

            Row row = sheet.createRow(rowNumber++);

            row.createCell(0)
                    .setCellValue(employee.getId());

            row.createCell(1)
                    .setCellValue(employee.getEmployeeCode());

            row.createCell(2)
                    .setCellValue(employee.getFullName());

            row.createCell(3)
                    .setCellValue(employee.getEmail());

            row.createCell(4)
                    .setCellValue(employee.getPhone());

            row.createCell(5)
                    .setCellValue(
                            employee.getDepartment() != null
                                    ? employee.getDepartment()
                                    .getDepartmentName()
                                    : "");

            row.createCell(6)
                    .setCellValue(employee.getDesignation());

            row.createCell(7)
                    .setCellValue(employee.getSalary());

            row.createCell(8)
                    .setCellValue(
                            employee.getJoiningDate() != null
                                    ? employee.getJoiningDate()
                                    .toString()
                                    : "");

            row.createCell(9)
                    .setCellValue(
                            employee.getDateOfBirth() != null
                                    ? employee.getDateOfBirth()
                                    .toString()
                                    : "");

            row.createCell(10)
                    .setCellValue(
                            employee.getStatus() != null
                                    ? employee.getStatus().name()
                                    : "");
        }

        for (int i = 0; i <= 10; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=employees.xlsx"
        );

        workbook.write(response.getOutputStream());

        workbook.close();
    }
    @Override
    public void exportAttendanceToPdf(
            HttpServletResponse response)
            throws IOException {

        List<Attendance> attendanceList =
                attendanceRepository.findAll();

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=attendance-report.pdf"
        );

        Document document = new Document();

        PdfWriter.getInstance(
                document,
                response.getOutputStream()
        );

        document.open();

        document.add(
                new Paragraph("DeskFloor - Attendance Report")
        );

        document.add(
                new Paragraph(" ")
        );

        PdfPTable table =
                new PdfPTable(7);

        table.addCell(new PdfPCell(
                new Phrase("ID")));

        table.addCell(new PdfPCell(
                new Phrase("Employee ID")));

        table.addCell(new PdfPCell(
                new Phrase("Employee Name")));

        table.addCell(new PdfPCell(
                new Phrase("Date")));

        table.addCell(new PdfPCell(
                new Phrase("Check In")));

        table.addCell(new PdfPCell(
                new Phrase("Check Out")));

        table.addCell(new PdfPCell(
                new Phrase("Working Hours")));

        for (Attendance attendance : attendanceList) {

            table.addCell(
                    String.valueOf(attendance.getId()));

            table.addCell(
                    String.valueOf(
                            attendance.getEmployee().getId()));

            table.addCell(
                    attendance.getEmployee().getFullName());

            table.addCell(
                    String.valueOf(
                            attendance.getAttendanceDate()));

            table.addCell(
                    attendance.getCheckInTime() != null
                            ? attendance.getCheckInTime().toString()
                            : "");

            table.addCell(
                    attendance.getCheckOutTime() != null
                            ? attendance.getCheckOutTime().toString()
                            : "");

            table.addCell(
                    attendance.getWorkingHours() != null
                            ? String.valueOf(
                            attendance.getWorkingHours())
                            : "");
        }

        document.add(table);

        document.close();
    }

    @Override
    public void exportLeavesToExcel(
            HttpServletResponse response)
            throws IOException {

        List<Leave> leaves =
                leaveRepository.findAll();

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet =
                workbook.createSheet("Leave Report");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("Leave ID");
        header.createCell(1).setCellValue("Employee ID");
        header.createCell(2).setCellValue("Employee Name");
        header.createCell(3).setCellValue("Leave Type");
        header.createCell(4).setCellValue("Start Date");
        header.createCell(5).setCellValue("End Date");
        header.createCell(6).setCellValue("Reason");
        header.createCell(7).setCellValue("Status");
        header.createCell(8).setCellValue("Applied Date");
        header.createCell(9).setCellValue("Approved Date");

        int rowNumber = 1;

        for (Leave leave : leaves) {

            Row row =
                    sheet.createRow(rowNumber++);

            row.createCell(0)
                    .setCellValue(leave.getId());

            row.createCell(1)
                    .setCellValue(
                            leave.getEmployee().getId());

            row.createCell(2)
                    .setCellValue(
                            leave.getEmployee().getFullName());

            row.createCell(3)
                    .setCellValue(
                            leave.getLeaveType().name());

            row.createCell(4)
                    .setCellValue(
                            leave.getStartDate().toString());

            row.createCell(5)
                    .setCellValue(
                            leave.getEndDate().toString());

            row.createCell(6)
                    .setCellValue(
                            leave.getReason());

            row.createCell(7)
                    .setCellValue(
                            leave.getStatus().name());

            row.createCell(8)
                    .setCellValue(
                            leave.getAppliedDate().toString());

            row.createCell(9)
                    .setCellValue(
                            leave.getApprovedDate() != null
                                    ? leave.getApprovedDate().toString()
                                    : "");
        }

        for (int i = 0; i <= 9; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=leave-report.xlsx"
        );

        workbook.write(
                response.getOutputStream());

        workbook.close();
    }
    @Override
    public void exportMonthlyReportToPdf(
            int year,
            int month,
            HttpServletResponse response)
            throws IOException {

        YearMonth yearMonth;

        try {
            yearMonth = YearMonth.of(year, month);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid year or month");
        }

        java.time.LocalDate startDate =
                yearMonth.atDay(1);

        java.time.LocalDate endDate =
                yearMonth.atEndOfMonth();

        // Employees
        List<Employee> employees =
                employeeRepository.findAll();

        // Attendance for the selected month
        List<Attendance> attendanceList =
                attendanceRepository.findAll()
                        .stream()
                        .filter(attendance ->
                                !attendance.getAttendanceDate()
                                        .isBefore(startDate)
                                        &&
                                        !attendance.getAttendanceDate()
                                                .isAfter(endDate))
                        .collect(Collectors.toList());

        // Leaves overlapping the selected month
        List<Leave> leaveList =
                leaveRepository.findAll()
                        .stream()
                        .filter(leave ->
                                !leave.getStartDate()
                                        .isAfter(endDate)
                                        &&
                                        !leave.getEndDate()
                                                .isBefore(startDate))
                        .collect(Collectors.toList());

        // Attendance statistics
        long presentCount =
                attendanceList.stream()
                        .filter(a ->
                                a.getStatus() != null
                                        &&
                                        a.getStatus()
                                                .name()
                                                .equals("PRESENT"))
                        .count();

        long lateCount =
                attendanceList.stream()
                        .filter(a ->
                                a.getStatus() != null
                                        &&
                                        a.getStatus()
                                                .name()
                                                .equals("LATE"))
                        .count();

        long halfDayCount =
                attendanceList.stream()
                        .filter(a ->
                                a.getStatus() != null
                                        &&
                                        a.getStatus()
                                                .name()
                                                .equals("HALF_DAY"))
                        .count();

        long absentCount =
                attendanceList.stream()
                        .filter(a ->
                                a.getStatus() != null
                                        &&
                                        a.getStatus()
                                                .name()
                                                .equals("ABSENT"))
                        .count();

        long approvedLeaveRequests =
                leaveList.stream()
                        .filter(leave ->
                                leave.getStatus()
                                        == LeaveStatus.APPROVED)
                        .count();

        long approvedLeaveDays =
                leaveList.stream()
                        .filter(leave ->
                                leave.getStatus()
                                        == LeaveStatus.APPROVED)
                        .mapToLong(leave -> {

                            java.time.LocalDate actualStart =
                                    leave.getStartDate()
                                            .isBefore(startDate)
                                            ? startDate
                                            : leave.getStartDate();

                            java.time.LocalDate actualEnd =
                                    leave.getEndDate()
                                            .isAfter(endDate)
                                            ? endDate
                                            : leave.getEndDate();

                            return java.time.temporal.ChronoUnit
                                    .DAYS
                                    .between(
                                            actualStart,
                                            actualEnd)
                                    + 1;
                        })
                        .sum();

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=monthly-report-"
                        + year
                        + "-"
                        + String.format("%02d", month)
                        + ".pdf"
        );

        Document document =
                new Document();

        PdfWriter.getInstance(
                document,
                response.getOutputStream()
        );

        document.open();

        // Title
        Paragraph title =
                new Paragraph(
                        "DeskFloor - Monthly Report",
                        new Font(Font.HELVETICA, 18, Font.BOLD)
                );

        document.add(title);

        document.add(
                new Paragraph(
                        "Report Period: "
                                + yearMonth
                )
        );

        document.add(
                new Paragraph(" ")
        );

        // Summary
        document.add(
                new Paragraph(
                        "Monthly Summary",
                        new Font(
                                Font.HELVETICA,
                                14,
                                Font.BOLD)
                )
        );

        PdfPTable summaryTable =
                new PdfPTable(2);

        summaryTable.addCell("Total Employees");
        summaryTable.addCell(
                String.valueOf(employees.size()));

        summaryTable.addCell("Attendance Records");
        summaryTable.addCell(
                String.valueOf(
                        attendanceList.size()));

        summaryTable.addCell("Present");
        summaryTable.addCell(
                String.valueOf(presentCount));

        summaryTable.addCell("Late");
        summaryTable.addCell(
                String.valueOf(lateCount));

        summaryTable.addCell("Half Day");
        summaryTable.addCell(
                String.valueOf(halfDayCount));

        summaryTable.addCell("Absent");
        summaryTable.addCell(
                String.valueOf(absentCount));

        summaryTable.addCell(
                "Approved Leave Requests");

        summaryTable.addCell(
                String.valueOf(
                        approvedLeaveRequests));

        summaryTable.addCell(
                "Approved Leave Days");

        summaryTable.addCell(
                String.valueOf(
                        approvedLeaveDays));

        document.add(summaryTable);

        document.add(
                new Paragraph(" ")
        );

        // Attendance details
        document.add(
                new Paragraph(
                        "Attendance Details",
                        new Font(
                                Font.HELVETICA,
                                14,
                                Font.BOLD)
                )
        );

        PdfPTable attendanceTable =
                new PdfPTable(7);

        attendanceTable.addCell("ID");
        attendanceTable.addCell("Employee");
        attendanceTable.addCell("Date");
        attendanceTable.addCell("Check In");
        attendanceTable.addCell("Check Out");
        attendanceTable.addCell("Working Hours");
        attendanceTable.addCell("Status");

        for (Attendance attendance :
                attendanceList) {

            attendanceTable.addCell(
                    String.valueOf(
                            attendance.getId()));

            attendanceTable.addCell(
                    attendance.getEmployee()
                            .getFullName());

            attendanceTable.addCell(
                    attendance.getAttendanceDate()
                            .toString());

            attendanceTable.addCell(
                    attendance.getCheckInTime() != null
                            ? attendance.getCheckInTime()
                            .toString()
                            : "");

            attendanceTable.addCell(
                    attendance.getCheckOutTime() != null
                            ? attendance.getCheckOutTime()
                            .toString()
                            : "");

            attendanceTable.addCell(
                    attendance.getWorkingHours() != null
                            ? String.valueOf(
                            attendance.getWorkingHours())
                            : "");

            attendanceTable.addCell(
                    attendance.getStatus() != null
                            ? attendance.getStatus()
                            .name()
                            : "");
        }

        document.add(attendanceTable);

        document.add(
                new Paragraph(" ")
        );

        // Leave details
        document.add(
                new Paragraph(
                        "Leave Details",
                        new Font(
                                Font.HELVETICA,
                                14,
                                Font.BOLD)
                )
        );

        PdfPTable leaveTable =
                new PdfPTable(6);

        leaveTable.addCell("Leave ID");
        leaveTable.addCell("Employee");
        leaveTable.addCell("Leave Type");
        leaveTable.addCell("Start Date");
        leaveTable.addCell("End Date");
        leaveTable.addCell("Status");

        for (Leave leave : leaveList) {

            leaveTable.addCell(
                    String.valueOf(
                            leave.getId()));

            leaveTable.addCell(
                    leave.getEmployee()
                            .getFullName());

            leaveTable.addCell(
                    leave.getLeaveType()
                            .name());

            leaveTable.addCell(
                    leave.getStartDate()
                            .toString());

            leaveTable.addCell(
                    leave.getEndDate()
                            .toString());

            leaveTable.addCell(
                    leave.getStatus()
                            .name());
        }

        document.add(leaveTable);

        document.close();
    }
}