package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.*;
import com.example.pgbuddy.models.*;
import com.example.pgbuddy.services.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // Allow requests from React
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/signup")
    public User signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return authService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
    }

    @PostMapping("/api/signin")
    public SignInResponseDto signIn(@RequestBody SignUpRequestDto signUpRequestDto) {
        return authService.signIn(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
    }
}
