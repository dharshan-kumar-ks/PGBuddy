package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.UserDto;
import com.example.pgbuddy.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user-related requests.
 * Provides endpoints to retrieve and update user details.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService The service for handling user-related business logic.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves user details by their ID.
     * This endpoint is used for features like Roommate Finder or profile view.
     *
     * @param id The ID of the user to retrieve.
     * @return A ResponseEntity containing the UserDto with the user's details.
     */
    @Operation(summary = "Retrieve user details by ID", description = "Fetches user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user details"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    // Get user by ID (for Roommate Finder or profile view)
    // This method will return the user details based on the provided ID
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Fills in the missing user details (excluding email and password).
     * Accepts user details from the client and saves them in the database.
     *
     * @param userDto The request body containing the user details to be filled.
     * @return A ResponseEntity containing the updated UserDto with the filled details.
     */
    @Operation(summary = "Retrieve user details by ID", description = "Fetches user details for Roommate Finder or profile view.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user details"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    // POST to fill in the missing user details (other than email and password)
    // This method will accept the user details from the client and save it in the database
    @PostMapping("/fill-details")
    public ResponseEntity<UserDto> fillUserDetails(@RequestBody UserDto userDto) {
        UserDto filledUser = userService.fillUserDetails(userDto);
        return ResponseEntity.ok(filledUser);
    }

}
