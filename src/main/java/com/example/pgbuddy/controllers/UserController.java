package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.UserDto;
import com.example.pgbuddy.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get user by ID (for Roommate Finder or profile view)
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

}
