package com.example.pgbuddy.Dtos;

import lombok.Data;

@Data
public class RentPaymentDto {
    private String rentPaymentId;
    private Float amount;
    private String transactionDate;
    private String paymentStatus;
    private Long userId;

    // Additional fields can be added as per requirements
}
