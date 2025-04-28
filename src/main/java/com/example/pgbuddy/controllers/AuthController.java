package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.*;
import com.example.pgbuddy.models.*;
import com.example.pgbuddy.services.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication-related requests, including user signup and signin.
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "https://pg-buddy-front-end.vercel.app"}) // Allow requests from React frontend// Allow requests from React frontend
public class AuthController {
    // Service for handling authentication logic
    private final AuthService authService;

    /**
     * Constructor to inject the AuthService dependency.
     *
     * @param authService The authentication service to handle business logic for authentication.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles user signup requests.
     *
     * @param signUpRequestDto The request body containing the user's email and password for signup.
     * @return The created User object after successful signup.
     */
    // POST method for user signup
    @PostMapping("/api/signup")
    public User signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        // Call the signUp method of AuthService with the email and password from the request -> return the User object
        return authService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
    }

    /**
     * Handles user signin requests.
     *
     * @param signUpRequestDto The request body containing the user's email and password for signin.
     * @return A SignInResponseDto object containing authentication details after successful signin.
     */
    // POST method for user signin
    @PostMapping("/api/signin")
    public SignInResponseDto signIn(@RequestBody SignUpRequestDto signUpRequestDto) {
        // Call the signIn method of AuthService with the email and password from the request -> return the SignInResponseDto object
        return authService.signIn(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
    }
}
