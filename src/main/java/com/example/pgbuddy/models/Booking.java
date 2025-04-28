package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Model class representing Booking details.
 * Maps to the "bookings" table in the database.
 */
@Data
@Entity
@Table(name = "bookings")
public class Booking extends BaseModel {
    private Date tenureStartDate;
    private Date tenureEndDate;
    private float monthlyRent;
    private float discountPercent;
    private float totalAmountPaidTillDate;
    private float DuesRemaining;

    // 1 Booking is only allowed for 1 User (1:1)
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 1 Booking can have multiple Payments (1:M)
    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER)
    List<Payment> payments;
}
