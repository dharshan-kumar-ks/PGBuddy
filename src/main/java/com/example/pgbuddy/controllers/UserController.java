package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.UserDto;
import com.example.pgbuddy.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // POST to fill in the missing user details (other than email and password)
    @PostMapping("/fill-details")
    public ResponseEntity<UserDto> fillUserDetails(@RequestBody UserDto userDto) {
        UserDto filledUser = userService.fillUserDetails(userDto);
        return ResponseEntity.ok(filledUser);
    }

}
