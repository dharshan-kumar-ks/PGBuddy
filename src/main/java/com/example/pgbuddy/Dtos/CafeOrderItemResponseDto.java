package com.example.pgbuddy.Dtos;

import lombok.Data;

@Data
public class CafeOrderItemResponseDto {
    private Long cafeMenuId; // ID of the menu item being ordered
    private String cafeMenuName; // Name of the menu item
    private int quantity; // Quantity of the menu item being ordered
    private float cafeMenuPrice; // Total price of the menu item => price * quantity
}
