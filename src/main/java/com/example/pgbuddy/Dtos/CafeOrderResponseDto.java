package com.example.pgbuddy.Dtos;

import lombok.Data;

import java.util.List;

// DTO class for Cafe Order response
@Data
public class CafeOrderResponseDto {
    //private Long user; // User ID
    private String orderStatus; // Order status (e.g., "pending")
    private List<CafeOrderItemResponseDto> cafeOrderItems; // List of items
    private float totalPrice; // Total price of the order
    private String orderDate;
}
