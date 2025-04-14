package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.InternetDataAddOnDto;
import com.example.pgbuddy.Dtos.InternetDeviceAddOnDto;
import com.example.pgbuddy.Dtos.InternetUsageDto;
import com.example.pgbuddy.utils.JwtUtil;
import com.example.pgbuddy.services.InternetUsageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/internet")
public class InternetUsageController {
    private final InternetUsageService internetUsageService;
    private final JwtUtil jwtUtil;

    public InternetUsageController(InternetUsageService internetUsageService, JwtUtil jwtUtil) {
        this.internetUsageService = internetUsageService;
        this.jwtUtil = jwtUtil;
    }

    // GET method to fetch internet usage data (for the specific user)
    @GetMapping("/usage")
    public ResponseEntity<InternetUsageDto> getInternetUsage(@RequestHeader("Authorization") String token) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix

        // Call the service to get internet usage data
        InternetUsageDto internetUsage = internetUsageService.getInternetUsage(userId);
        return ResponseEntity.ok(internetUsage);
    }

    // GET method to fetch data add-on options
    @GetMapping("/data-add-ons")
    public ResponseEntity<List<InternetDataAddOnDto>> getDataAddOnOptions() {
        // Call the service to get data add-on options
        List<InternetDataAddOnDto> addOnOptions = internetUsageService.getDataAddOnOptions();
        return ResponseEntity.ok(addOnOptions);
    }

    // GET method to fetch device add-on options
    @GetMapping("/device-add-ons")
    public ResponseEntity<List<InternetDeviceAddOnDto>> getDeviceAddOnOptions() {
        // Call the service to get data add-on options
        List<InternetDeviceAddOnDto> addOnOptions = internetUsageService.getDeviceAddOnOptions();
        return ResponseEntity.ok(addOnOptions);
    }


}
