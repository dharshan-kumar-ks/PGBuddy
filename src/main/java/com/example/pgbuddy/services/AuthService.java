package com.example.pgbuddy.services;

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

    /*
    public User signUp(String email, String password) {
        // Check if user already exist & get that user in DB (based on the email)
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        // if User is not present in DB -> create this new user & save in userRepository
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }
    */

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

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();
        return password.equals(user.getPassword());
    }

}
