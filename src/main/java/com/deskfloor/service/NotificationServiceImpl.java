package com.deskfloor.service;

import com.deskfloor.entity.Employee;
import com.deskfloor.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    public NotificationServiceImpl(
            EmployeeRepository employeeRepository,
            EmailService emailService) {

        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
    }

    @Override
    public void sendWorkAnniversaryNotifications() {

        LocalDate today = LocalDate.now();

        List<Employee> employees =
                employeeRepository.findAll();

        for (Employee employee : employees) {

            if (employee.getJoiningDate() == null) {
                continue;
            }

            LocalDate joiningDate =
                    employee.getJoiningDate();

            if (joiningDate.getMonthValue() == today.getMonthValue()
                    && joiningDate.getDayOfMonth() == today.getDayOfMonth()) {

                int years =
                        today.getYear() - joiningDate.getYear();

                String subject =
                        "Happy Work Anniversary - Deskfloor HRMS";

                String body =
                        "Hello " + employee.getFullName() + ",\n\n"
                                + "Congratulations on your work anniversary!\n\n"
                                + "You have completed "
                                + years
                                + " year(s) with the organization.\n\n"
                                + "Joining Date: "
                                + joiningDate
                                + "\n\n"
                                + "We appreciate your contribution and dedication.\n\n"
                                + "Regards,\n"
                                + "Deskfloor HRMS";

                emailService.sendEmail(
                        employee.getEmail(),
                        subject,
                        body
                );
            }
        }
    }
    @Override
    public void sendBirthdayNotifications() {

        LocalDate today = LocalDate.now();

        List<Employee> employees =
                employeeRepository.findAll();

        for (Employee employee : employees) {

            if (employee.getDateOfBirth() == null) {
                continue;
            }

            LocalDate dateOfBirth =
                    employee.getDateOfBirth();

            if (dateOfBirth.getMonthValue() == today.getMonthValue()
                    && dateOfBirth.getDayOfMonth() == today.getDayOfMonth()) {

                String subject =
                        "Happy Birthday - Deskfloor HRMS";

                String body =
                        "Hello " + employee.getFullName() + ",\n\n"
                                + "Wishing you a very Happy Birthday! 🎉\n\n"
                                + "We hope you have a wonderful day "
                                + "filled with happiness and success.\n\n"
                                + "Regards,\n"
                                + "Deskfloor HRMS";

                emailService.sendEmail(
                        employee.getEmail(),
                        subject,
                        body
                );
            }
        }
    }
    @Scheduled(cron = "0 0 9 * * *")
    public void processDailyNotifications() {

        sendWorkAnniversaryNotifications();
        sendBirthdayNotifications();
    }


}