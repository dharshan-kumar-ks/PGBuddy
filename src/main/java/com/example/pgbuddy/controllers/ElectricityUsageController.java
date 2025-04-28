package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.ElectricityUsageDto;
import com.example.pgbuddy.utils.JwtUtil;
import com.example.pgbuddy.services.ElectricityUsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling electricity usage-related requests.
 * Provides an endpoint to retrieve electricity usage data for a specific user.
 */
@RestController
@RequestMapping("/api/electricity/usage")
public class ElectricityUsageController {
    private final ElectricityUsageService electricityUsageService;
    private final JwtUtil jwtUtil;

    /**
     * Constructor for ElectricityUsageController.
     *
     * @param electricityUsageService The service for handling electricity usage-related business logic.
     * @param jwtUtil The utility for handling JWT operations, such as extracting userId from tokens.
     */
    public ElectricityUsageController(ElectricityUsageService electricityUsageService, JwtUtil jwtUtil) {
        this.electricityUsageService = electricityUsageService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Retrieves electricity usage data for a specific user based on the provided JWT token.
     *
     * @param token The Authorization header containing the JWT token.
     * @return A ResponseEntity containing the ElectricityUsageDto with electricity usage data.
     */
    @Operation(
            summary = "Retrieve electricity usage data",
            description = "Fetches electricity usage data for a specific user based on the provided JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Electricity usage data retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    // GET method to fetch electricity usage data (for the specific user)
    @GetMapping
    public ResponseEntity<ElectricityUsageDto> getElectricityUsage(@RequestHeader("Authorization") String token) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix

        // Call the service to get electricity usage data - for this specific user
        ElectricityUsageDto electricityUsage = electricityUsageService.getElectricityUsage(userId);
        return ResponseEntity.ok(electricityUsage);
    }

}
