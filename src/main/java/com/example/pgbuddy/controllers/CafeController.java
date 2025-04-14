package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.CafeOrderRequestDto;
import com.example.pgbuddy.Dtos.CafeOrderResponseDto;
import com.example.pgbuddy.utils.JwtUtil;
import com.example.pgbuddy.models.CafeMenu;
import com.example.pgbuddy.services.CafeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cafe")
public class CafeController {
    private final CafeService cafeService;
    private final JwtUtil jwtUtil; // JWT utility for extracting userId from token

    public CafeController(CafeService cafeService, JwtUtil jwtUtil) {
        this.cafeService = cafeService;
        this.jwtUtil = jwtUtil;
    }

    // GET method to display all the cafe items available from the 'cafe_menu' table in DB
    // This method will return a list of all menu items
    @GetMapping("/menu")
    public ResponseEntity<List<CafeMenu>> getAllMenuItems() {
        List<CafeMenu> menuItems = cafeService.getAllMenuItems();
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    // POST method to save the cafe order details in the DB
    // This method will accept the order details from the client and save it in the database
    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody CafeOrderRequestDto cafeOrderRequest) {
        cafeService.placeOrder(cafeOrderRequest);
        return new ResponseEntity<>("Order placed successfully", HttpStatus.CREATED);
    }

    // GET method to get all the order details
    // This method will return a list of all orders
    @GetMapping("/orders")
    public ResponseEntity<List<CafeOrderResponseDto>> getAllOrders(@RequestHeader("Authorization") String token) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix
        List<CafeOrderResponseDto> orders = cafeService.getAllOrders(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
