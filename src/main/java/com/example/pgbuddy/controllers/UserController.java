package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.*;
import com.example.pgbuddy.models.*;
import com.example.pgbuddy.services.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return userService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
    }

    @PostMapping("/signin")
    public boolean signIn(@RequestBody SignUpRequestDto signUpRequestDto) {
        return userService.signIn(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
    }
}
