package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.SignInResponseDto;
import com.example.pgbuddy.models.*;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(String email, String password) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                throw new IllegalArgumentException("User already exists");
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            System.out.println("Saving user: " + user.getEmail()); // ✅ Debugging

            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace(); // ✅ Logs error details
            throw new RuntimeException("Signup failed: " + e.getMessage());
        }
    }

    public SignInResponseDto signIn(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return new SignInResponseDto(true, user.get().getId());
        }
        return new SignInResponseDto(false, null);
    }

}
