package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository interface for Payment entity
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByRazorpayOrderId(String razorpayOrderId);
}
