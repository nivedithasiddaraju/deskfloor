package com.deskfloor.controller;

import com.deskfloor.dto.AuthResponse;
import com.deskfloor.dto.ForgotPasswordRequest;
import com.deskfloor.dto.LoginRequest;
import com.deskfloor.dto.RegisterRequest;
import com.deskfloor.dto.ResetPasswordRequest;
import com.deskfloor.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.deskfloor.dto.ChangePasswordRequest;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        return authService.forgotPassword(request);
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        return authService.resetPassword(request);
    }
    @PostMapping("/change-password")
    public String changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return authService.changePassword(
                request,
                email
        );
    }
    @PostMapping("/logout")
    public String logout() {

        return "Logout successful";
    }
}