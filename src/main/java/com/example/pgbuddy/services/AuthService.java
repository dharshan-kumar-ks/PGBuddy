package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.SignInResponseDto;
import com.example.pgbuddy.utils.JwtUtil;
import com.example.pgbuddy.models.*;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling authentication-related operations.
 */
@Service
public class AuthService {
    private UserRepository userRepository;
    private final JwtUtil jwtUtil;
    // we ourselves create the object for this BCryptPasswordEncoder instead of Spring creating it
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Constructor for AuthService.
     *
     * @param userRepository The repository for accessing user data.
     * @param jwtUtil Utility for generating and validating JWT tokens.
     */
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registers a new user in the system.
     *
     * @param email The email address of the user.
     * @param password The plain text password of the user.
     * @return The newly created User object.
     * @throws IllegalArgumentException If a user with the given email already exists.
     * @throws RuntimeException If the signup process fails.
     */
    // POST method for user signup
    public User signUp(String email, String password) {
        try {
            // Get the user in DB (based on the email) if it exists
            Optional<User> userOptional = userRepository.findByEmail(email);
            // If the user already exists, throw an exception since we don't want duplicate users
            if (userOptional.isPresent()) {
                throw new IllegalArgumentException("User already exists");
            }

            // Create a new user object and set its email and password in user DB table
            User user = new User();
            user.setEmail(email);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            System.out.println("Saving user: " + user.getEmail()); // Logs the email of the user being saved (for debugging)

            return userRepository.save(user); // Save the user to the database
        } catch (Exception e) {
            e.printStackTrace(); // ✅ Logs error details
            throw new RuntimeException("Signup failed: " + e.getMessage());
        }
    }

    /**
     * Authenticates a user and generates a JWT token if credentials are valid.
     *
     * @param email The email address of the user.
     * @param password The plain text password of the user.
     * @return A SignInResponseDto containing the authentication result, user details, and JWT token.
     */
    // POST method for user signin
    public SignInResponseDto signIn(String email, String password) {
        // Get the user in DB (based on the email) if it exists
        Optional<User> user = userRepository.findByEmail(email);
        // Validate user credentials -> return back the userId and JWT token to client (& a success flag)
        if (user.isPresent() && bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
            // Generate a JWT token for the user
            String token = jwtUtil.generateToken(user.get().getId(), email, String.valueOf(user.get().getUserType()));

            // Create a SignInResponseDto object & set the success flag, userId, userRole, and token -> to return the response
            SignInResponseDto response = new SignInResponseDto();
            response.setSuccess(true);
            response.setUserId(user.get().getId());
            response.setUserRole(String.valueOf(user.get().getUserType()));
            response.setToken(token);
            return response;
        }
        // Return a failure response if authentication fails
        // If the user is not found or the password doesn't match, return a SignInResponseDto with success flag as false & other fields as null
        return new SignInResponseDto(false, null, null, null);

    }

}
