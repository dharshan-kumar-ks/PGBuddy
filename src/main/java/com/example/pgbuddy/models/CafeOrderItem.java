package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

// CafeOrderItem class represents an item in a cafe order
@Data
@Entity
@Table(name = "cafe_order_items")
// Didn't extend base model
public class CafeOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Only 1 CafeMenu items can be part of 1 order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_menu_id", nullable = false, unique = false)
    private CafeMenu cafeMenu; // menu item being ordered

    private int quantity; // Quantity of the menu item being ordered

    // Multiple CafeOrderItems can be part of 1 order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private CafeOrder cafeOrder;
}
