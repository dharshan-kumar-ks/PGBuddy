package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.BookingDto;
import com.example.pgbuddy.Dtos.PaymentTransactionDto;
import com.example.pgbuddy.exceptions.ResourceNotFoundException;
import com.example.pgbuddy.models.Booking;
import com.example.pgbuddy.repositories.BookingRepository;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
    }

    // GET method to get booking details for the user
    public BookingDto getBookingDetails(Long userId) {
        // Fetch booking by userId
        Booking booking = bookingRepository.findByUserId(userId);
        if (booking == null) {
            throw new ResourceNotFoundException("Booking not found for userId: " + userId);
        }

        // Convert Booking entity to BookingDto
        BookingDto bookingDto = new BookingDto();
        bookingDto.setBookingId(booking.getId());
        bookingDto.setTenureStartDate(booking.getTenureStartDate());
        bookingDto.setTenureEndDate(booking.getTenureEndDate());
        bookingDto.setMonthlyRent(booking.getMonthlyRent());
        bookingDto.setDiscountPercent(booking.getDiscountPercent());
        bookingDto.setTotalAmountPaidTillDate(booking.getTotalAmountPaidTillDate());
        bookingDto.setDuesRemaining(booking.getDuesRemaining());
        bookingDto.setUserId(booking.getUser().getId());

        return bookingDto;
    }

    // GET method to get the list of payment transaction details of user
    public List<PaymentTransactionDto> getPaymentTransactions(Long userId) {
        // Fetch booking by userId
        Booking booking = bookingRepository.findByUserId(userId);
        if (booking == null) {
            throw new ResourceNotFoundException("Booking not found for userId: " + userId);
        }

        // Extract the List of Payment objects from the Booking entity
        List<PaymentTransactionDto> paymentTransactions = booking.getPayments()
                .stream()
                .map(payment -> {
                    PaymentTransactionDto paymentTransactionDto = new PaymentTransactionDto();
                    paymentTransactionDto.setTransactionId(payment.getId().toString());
                    paymentTransactionDto.setAmount(payment.getAmount());
                    paymentTransactionDto.setTransactionDate(payment.getCreatedAt() != null ? payment.getCreatedAt().toString() : "N/A");
                    paymentTransactionDto.setPaymentStatus(payment.getPaymentStatus().toString());
                    paymentTransactionDto.setUserId(userId);
                    return paymentTransactionDto;
                })
                .toList();
        return paymentTransactions;
    }
}





