package com.example.pgbuddy.Dtos;

import lombok.Data;

import java.util.Date;

// DTO class for Booking details
@Data
public class BookingDto {
    private Long bookingId;
    private Date tenureStartDate;
    private Date tenureEndDate;
    private float monthlyRent;
    private float discountPercent;
    private float totalAmountPaidTillDate;
    private float DuesRemaining;

    private Long userId;
}
