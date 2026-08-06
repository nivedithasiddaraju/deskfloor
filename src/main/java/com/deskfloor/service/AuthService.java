package com.deskfloor.service;

import com.deskfloor.dto.AuthResponse;
import com.deskfloor.dto.LoginRequest;
import com.deskfloor.dto.RegisterRequest;
import com.deskfloor.entity.User;
import com.deskfloor.enums.Role;
import com.deskfloor.repository.UserRepository;
import com.deskfloor.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, "Email already exists");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "Login Successful");
    }

}