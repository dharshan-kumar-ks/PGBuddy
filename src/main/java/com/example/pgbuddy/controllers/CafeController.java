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

/**
 * Controller for handling cafe-related requests, such as retrieving menu items,
 * placing orders, and fetching order details.
 */
@RestController
@RequestMapping("/api/cafe")
public class CafeController {
    private final CafeService cafeService;
    private final JwtUtil jwtUtil; // JWT utility for extracting userId from token

    /**
     * Constructor for CafeController.
     *
     * @param cafeService The service for handling cafe-related business logic.
     * @param jwtUtil The utility for handling JWT operations, such as extracting userId from tokens.
     */
    public CafeController(CafeService cafeService, JwtUtil jwtUtil) {
        this.cafeService = cafeService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Retrieves all the cafe menu items available in the database.
     *
     * @return A ResponseEntity containing a list of CafeMenu objects and an HTTP status code.
     */
    // GET method to display all the cafe items available from the 'cafe_menu' table in DB
    // This method will return a list of all menu items
    @GetMapping("/menu")
    public ResponseEntity<List<CafeMenu>> getAllMenuItems() {
        List<CafeMenu> menuItems = cafeService.getAllMenuItems(); // Get all menu items from the cafe service
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    /**
     * Places a cafe order by saving the order details in the database.
     *
     * @param cafeOrderRequest The request body containing the details of the cafe order.
     * @return A ResponseEntity containing a success message and an HTTP status code.
     */
    // POST method to save the cafe order details in the DB
    // This method will accept the order details from the client and save it in the database
    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody CafeOrderRequestDto cafeOrderRequest) {
        cafeService.placeOrder(cafeOrderRequest); // Place the order using the cafe service
        return new ResponseEntity<>("Order placed successfully", HttpStatus.CREATED);
    }

    /**
     * Retrieves all the cafe orders for a specific user based on the provided JWT token.
     *
     * @param token The Authorization header containing the JWT token.
     * @return A ResponseEntity containing a list of CafeOrderResponseDto objects and an HTTP status code.
     */
    // GET method to get all the order details
    // This method will return a list of all orders
    @GetMapping("/orders")
    public ResponseEntity<List<CafeOrderResponseDto>> getAllOrders(@RequestHeader("Authorization") String token) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix & extract userId from the token
        List<CafeOrderResponseDto> orders = cafeService.getAllOrders(userId); // Get all orders using the cafe service
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
