package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.InternetDataAddOnDto;
import com.example.pgbuddy.Dtos.InternetDeviceAddOnDto;
import com.example.pgbuddy.Dtos.InternetUsageDto;
import com.example.pgbuddy.utils.JwtUtil;
import com.example.pgbuddy.services.InternetUsageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // POST method to update internet usage data based on the selected data add-on
    @PostMapping("/update/data/{id}")
    public ResponseEntity<String> updateInternetUsage(@RequestHeader("Authorization") String token,
                                                     @PathVariable Long id) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix
        // Call the service to update internet usage data
        internetUsageService.updateInternetUsageForData(userId, id);
        return ResponseEntity.ok("Recharge successful");
    }

    // POST method to update internet usage data based on the selected device add-on
    @PostMapping("/update/device/{id}")
    public ResponseEntity<String> updateInternetDevice(@RequestHeader("Authorization") String token,
                                                       @PathVariable Long id) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix
        // Call the service to update internet usage data
        internetUsageService.updateInternetUsageForDevice(userId, id);
        return ResponseEntity.ok("Recharge successful");
    }

}
