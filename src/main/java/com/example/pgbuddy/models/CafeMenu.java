package com.example.pgbuddy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

// Model class for Cafe Menu details
@Data
@Entity
@Table(name = "cafe_menu")
public class CafeMenu extends BaseModel {
    private String name;
    private String description;
    private double price;
    private String imageUrl; // URL or path to the image of the menu item
    private boolean isAvailable; // Indicates if the item is available for order
    private boolean isSpicy; // Indicates if the item is spicy
    private boolean isNonVeg; // Indicates if the item is non-vegetarian

    private Long totalOrders; // Total number of orders for this item - can include in bestsellers list if needed (later)
}

