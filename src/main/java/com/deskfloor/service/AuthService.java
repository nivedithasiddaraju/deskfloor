package com.deskfloor.service;

import com.deskfloor.dto.AuthResponse;
import com.deskfloor.dto.ForgotPasswordRequest;
import com.deskfloor.dto.LoginRequest;
import com.deskfloor.dto.RegisterRequest;
import com.deskfloor.dto.ResetPasswordRequest;
import com.deskfloor.entity.User;
import com.deskfloor.enums.Role;
import com.deskfloor.repository.UserRepository;
import com.deskfloor.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.deskfloor.dto.ChangePasswordRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, "Email already exists");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole(Role.EMPLOYEE);

        userRepository.save(user);

        return new AuthResponse(null, "Registration Successful");
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return new AuthResponse(null, "User not found");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return new AuthResponse(null, "Invalid Password");
        }

        String token =
                jwtService.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                "Login Successful"
        );
    }

    public String forgotPassword(
            ForgotPasswordRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String resetToken =
                UUID.randomUUID().toString();

        LocalDateTime expiry =
                LocalDateTime.now().plusMinutes(15);

        user.setResetToken(resetToken);
        user.setResetTokenExpiry(expiry);

        userRepository.save(user);

        String resetLink =
                "http://localhost:8080/api/auth/reset-password?token="
                        + resetToken;

        String subject = "Deskfloor HRMS - Password Reset";

        String body =
                "Hello " + user.getFullName() + ",\n\n"
                        + "We received a request to reset your password.\n\n"
                        + "Click the following link to reset your password:\n\n"
                        + resetLink + "\n\n"
                        + "This link will expire in 15 minutes.\n\n"
                        + "If you did not request a password reset, "
                        + "please ignore this email.\n\n"
                        + "Regards,\n"
                        + "Deskfloor HRMS";

        emailService.sendEmail(
                user.getEmail(),
                subject,
                body
        );

        return "Password reset email sent successfully";
    }

    public String resetPassword(
            ResetPasswordRequest request) {

        User user = userRepository
                .findByResetToken(request.getToken())
                .orElseThrow(() ->
                        new RuntimeException("Invalid reset token"));

        if (user.getResetTokenExpiry() == null ||
                user.getResetTokenExpiry()
                        .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Reset token has expired");
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);

        return "Password reset successfully";
    }
    public String changePassword(
            ChangePasswordRequest request,
            String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Current password is incorrect");
        }

        if (request.getCurrentPassword()
                .equals(request.getNewPassword())) {

            throw new RuntimeException(
                    "New password must be different from current password");
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        return "Password changed successfully";
    }
}