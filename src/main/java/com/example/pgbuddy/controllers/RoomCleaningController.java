package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.RoomCleaningResponseDto;
import com.example.pgbuddy.utils.JwtUtil;
import com.example.pgbuddy.services.RoomCleaningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling room cleaning-related requests.
 * Provides endpoints to fetch room cleaning usage data and request room cleaning services.
 */
@RestController
@RequestMapping("/api/roomcleaning")
public class RoomCleaningController {
    private final RoomCleaningService roomCleaningService;
    private final JwtUtil jwtUtil;

    /**
     * Constructor for RoomCleaningController.
     *
     * @param roomCleaningService The service for handling room cleaning-related business logic.
     * @param jwtUtil The utility for handling JWT operations, such as extracting userId from tokens.
     */
    public RoomCleaningController(RoomCleaningService roomCleaningService, JwtUtil jwtUtil) {
        this.roomCleaningService = roomCleaningService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Retrieves room cleaning usage data for a specific user based on the provided JWT token.
     *
     * @param token The Authorization header containing the JWT token.
     * @return A ResponseEntity containing the RoomCleaningResponseDto with room cleaning usage data.
     */
    // GET method to fetch room cleaning data (for the specific user)
    @GetMapping("/usage")
    public ResponseEntity<RoomCleaningResponseDto> getRoomCleaningUsage(@RequestHeader("Authorization") String token) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix & extract userId from the token

        // Call the service to get room cleaning data
        RoomCleaningResponseDto roomCleaningUsage = roomCleaningService.getRoomCleaningUsage(userId);
        return ResponseEntity.ok(roomCleaningUsage);
    }

    /**
     * Handles a POST request to save a room cleaning request for the authenticated user.
     * Extracts the user ID from the provided JWT token and processes the room cleaning request.
     *
     * @param token The Authorization header containing the JWT token.
     * @return A ResponseEntity containing a success message upon successful request processing.
     */
    // POST method to save the room cleaning data
    @PostMapping("/request")
    public ResponseEntity<String> requestRoomCleaning(@RequestHeader("Authorization") String token) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix

        // Call the service to request room cleaning
        roomCleaningService.requestRoomCleaning(userId);
        return ResponseEntity.ok("Room cleaning requested successfully");
    }

}
