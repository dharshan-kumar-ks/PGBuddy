package com.example.pgbuddy.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class CafeOrderRequestDto {
    private Long user; // User ID
    private String orderStatus; // Order status (e.g., "pending")
    private List<CafeOrderItemRequestDto> cafeOrderItems; // List of items
}
