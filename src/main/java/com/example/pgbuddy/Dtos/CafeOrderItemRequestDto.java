package com.example.pgbuddy.Dtos;

import lombok.Data;

@Data
public class CafeOrderItemRequestDto {
    private Long cafeMenuId; // ID of the menu item being ordered
    private int quantity; // Quantity of the menu item being ordered
}
