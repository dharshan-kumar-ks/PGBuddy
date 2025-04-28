package com.example.pgbuddy.Dtos;

import lombok.Data;

// DTO class for Payment Transaction details
@Data
public class PaymentTransactionDto {
    private String transactionId;
    private Float amount;
    private String transactionDate;
    private String paymentStatus;
    private Long userId;
}
