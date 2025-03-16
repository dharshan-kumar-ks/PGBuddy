package com.example.pgbuddy.services;

import com.example.pgbuddy.models.*;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public boolean signIn(String email, String password) {
        // Get the user object using its email from DB
        User user = userRepository.findByEmail(email).get();

        return password == user.getPassword();
    }
}
