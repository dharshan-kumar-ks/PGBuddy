package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.ElectricityUsageDto;
import com.example.pgbuddy.utils.JwtUtil;
import com.example.pgbuddy.services.ElectricityUsageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/electricity/usage")
public class ElectricityUsageController {
    private final ElectricityUsageService electricityUsageService;
    private final JwtUtil jwtUtil;

    public ElectricityUsageController(ElectricityUsageService electricityUsageService, JwtUtil jwtUtil) {
        this.electricityUsageService = electricityUsageService;
        this.jwtUtil = jwtUtil;
    }

    // GET method to fetch electricity usage data (for the specific user)
    @GetMapping
    public ResponseEntity<ElectricityUsageDto> getElectricityUsage(@RequestHeader("Authorization") String token) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix

        // Call the service to get electricity usage data
        ElectricityUsageDto electricityUsage = electricityUsageService.getElectricityUsage(userId);
        return ResponseEntity.ok(electricityUsage);
    }

}
