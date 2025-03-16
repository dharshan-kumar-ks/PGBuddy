package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

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
}
