package org.cschwabe.controller;

import org.cschwabe.dto.LoginRequest;
import org.cschwabe.dto.LoginResponse;
import org.cschwabe.model.User;
import org.cschwabe.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        authService.register(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}