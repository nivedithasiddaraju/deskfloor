package com.deskfloor.controller;

import com.deskfloor.dto.ApiResponse;
import com.deskfloor.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    @PostMapping("/work-anniversary")
    public ResponseEntity<ApiResponse<String>>
    sendWorkAnniversaryNotifications() {

        notificationService.sendWorkAnniversaryNotifications();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Work anniversary notifications processed successfully",
                        "Completed"
                )
        );
    }
    @PostMapping("/birthday")
    public ResponseEntity<ApiResponse<String>>
    sendBirthdayNotifications() {

        notificationService.sendBirthdayNotifications();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Birthday notifications processed successfully",
                        "Completed"
                )
        );
    }
}