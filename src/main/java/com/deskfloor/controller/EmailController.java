package com.deskfloor.controller;

import com.deskfloor.dto.ApiResponse;
import com.deskfloor.dto.EmailRequest;
import com.deskfloor.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/test")
    public ResponseEntity<ApiResponse<String>> sendTestEmail(
            @Valid @RequestBody EmailRequest request) {

        emailService.sendEmail(
                request.getTo(),
                request.getSubject(),
                request.getBody()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Email sent successfully",
                        "Email sent to " + request.getTo()
                )
        );
    }
}