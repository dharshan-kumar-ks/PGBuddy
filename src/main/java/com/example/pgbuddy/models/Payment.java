package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

// Model class for Payment details
@Data
@Entity
@Table(name = "payments")
public class Payment extends BaseModel {
    // Multiple Payments can have 1 User (M:1)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private float amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // SUCCESS, FAILED, PROCESSING

    private String receiptUrl;

    // Multiple Payments can be part of 1 Booking (M:1)
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private String razorpayOrderId;
}
