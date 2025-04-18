package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.BookingDto;
import com.example.pgbuddy.Dtos.PaymentTransactionDto;
import com.example.pgbuddy.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // GET method to get booking details for the user
    // This method will accept booking ID from the client and return the booking details
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingDetails(@PathVariable("id") Long userId) {
        BookingDto bookingDto = bookingService.getBookingDetails(userId);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    // GET method to get the list of payment transaction details of user
    @GetMapping("/transactions/{id}")
    public ResponseEntity<List<PaymentTransactionDto>> getPaymentTransactions(@PathVariable("id") Long userId) {
        List<PaymentTransactionDto> transactions = bookingService.getPaymentTransactions(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // POST method to pay rent
    @PostMapping("/pay")
    public ResponseEntity<PaymentTransactionDto> payRent(@RequestBody PaymentTransactionDto paymentTransactionDto) {
        PaymentTransactionDto response = bookingService.payRent(paymentTransactionDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    // POST method to cancel booking
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable("id") Long userId) {
        String response = bookingService.cancelBooking(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    */

}

