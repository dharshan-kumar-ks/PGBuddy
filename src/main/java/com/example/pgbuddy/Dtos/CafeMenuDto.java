package com.example.pgbuddy.Dtos;

import lombok.Data;

@Data
public class CafeMenuDto {
    private String name;
    private String description;
    private double price;
    private String imageUrl; // URL or path to the image of the menu item
    //private boolean isAvailable; // Indicates if the item is available for order
    private boolean isSpicy; // Indicates if the item is spicy
    private boolean isNonVeg; // Indicates if the item is non-vegetarian
}
