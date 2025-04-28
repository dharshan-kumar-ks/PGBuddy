package com.example.pgbuddy.models;

// Enum for Order Status -> representing different statuses of an order
public enum OrderStatus {
    PENDING, // Order has been placed but not yet processed
    IN_PROGRESS, // Order is being prepared
    COMPLETED, // Order has been completed and is ready for pickup/delivery
    CANCELLED // Order has been cancelled
}
