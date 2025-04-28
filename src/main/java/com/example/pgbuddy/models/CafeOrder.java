package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

// CafeOrder class representing an order placed by a user in the cafe
@Data
@Entity
@Table(name = "cafe_orders")
public class CafeOrder extends BaseModel {
    // Multiple orders can be placed by 1 User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // The User placing the order

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // Status of the order (e.g., "PENDING", "IN_PROGRESS", "COMPLETED", "CANCELLED")

    // 1 CafeOrder can have multiple CafeOrderItems
    @OneToMany(mappedBy = "cafeOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CafeOrderItem> cafeOrderItems; // List of items in the order
    // orphan removal -> a feature that automatically deletes child entities when they are removed from the parent entity's collection or reference
    // If a CafeOrderItem is removed from the cafeOrderItems list of a CafeOrder, JPA will delete that CafeOrderItem from the database.
    // It is useful for maintaining data integrity and avoiding orphaned rows in the database.

    // Additional fields can be added as needed
}
