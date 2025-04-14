package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.RoomCleaningResponseDto;
import com.example.pgbuddy.utils.JwtUtil;
import com.example.pgbuddy.services.RoomCleaningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roomcleaning")
public class RoomCleaningController {
    private final RoomCleaningService roomCleaningService;
    private final JwtUtil jwtUtil;

    public RoomCleaningController(RoomCleaningService roomCleaningService, JwtUtil jwtUtil) {
        this.roomCleaningService = roomCleaningService;
        this.jwtUtil = jwtUtil;
    }

    // GET method to fetch room cleaning data (for the specific user)
    @GetMapping("/usage")
    public ResponseEntity<RoomCleaningResponseDto> getRoomCleaningUsage(@RequestHeader("Authorization") String token) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix

        // Call the service to get room cleaning data
        RoomCleaningResponseDto roomCleaningUsage = roomCleaningService.getRoomCleaningUsage(userId);
        return ResponseEntity.ok(roomCleaningUsage);
    }

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
