package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.SignInResponseDto;
import com.example.pgbuddy.JwtUtil;
import com.example.pgbuddy.models.*;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private final JwtUtil jwtUtil;
    // we ourselves create the object for this BCryptPasswordEncoder instead of Spring creating it
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User signUp(String email, String password) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                throw new IllegalArgumentException("User already exists");
            }

            User user = new User();
            user.setEmail(email);
            //user.setPassword(password);
            // Saving encrypted password in DB
            user.setPassword(bCryptPasswordEncoder.encode(password));

            System.out.println("Saving user: " + user.getEmail()); // ✅ Debugging

            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace(); // ✅ Logs error details
            throw new RuntimeException("Signup failed: " + e.getMessage());
        }
    }

    public SignInResponseDto signIn(String email, String password) {
        // get the user corresponding to the email from DB
        Optional<User> user = userRepository.findByEmail(email);
        // Validate user credentials -> return back the userId and JWT token to client (& a success flag)
        if (user.isPresent() && bCryptPasswordEncoder.matches(password, user.get().getPassword())) {

            String token = jwtUtil.generateToken(email, String.valueOf(user.get().getUserType()));

            SignInResponseDto response = new SignInResponseDto();
            response.setSuccess(true);
            response.setUserId(user.get().getId());
            response.setToken(token);
            return response;
        }
        return new SignInResponseDto(false, null, null);

        /*
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return new SignInResponseDto(true, user.get().getId());
        }
        return new SignInResponseDto(false, null);
         */
    }

}
