package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.BookingDto;
import com.example.pgbuddy.Dtos.PaymentTransactionDto;
import com.example.pgbuddy.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for handling booking-related requests, such as retrieving booking details,
 * fetching payment transactions, processing rent payments, and verifying payment signatures.
 */
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    /**
     * Constructor for BookingController.
     *
     * @param bookingService The service for handling booking-related business logic.
     */
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Retrieves booking details for a specific user based on the booking ID.
     *
     * @param userId The ID of the user whose booking details are to be retrieved.
     * @return A ResponseEntity containing the BookingDto with booking details and an HTTP status code.
     */
    // GET method to get booking details for the user
    // This method will accept booking ID from the client and return the booking details
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingDetails(@PathVariable("id") Long userId) {
        BookingDto bookingDto = bookingService.getBookingDetails(userId); // Get booking details using the booking ID (using booking service)
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    /**
     * Retrieves a list of payment transaction details for a specific user based on their user ID.
     *
     * @param userId The ID of the user whose payment transactions are to be retrieved.
     * @return A ResponseEntity containing a list of PaymentTransactionDto objects and an HTTP status code.
     */
    // GET method to get the list of payment transaction details of user (based on userId)
    // This method will accept user ID from the client and return the list of payment transactions
    @GetMapping("/transactions/{id}")
    public ResponseEntity<List<PaymentTransactionDto>> getPaymentTransactions(@PathVariable("id") Long userId) {
        List<PaymentTransactionDto> transactions = bookingService.getPaymentTransactions(userId); // Get payment transactions using the user ID (using booking service)
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Processes a rent payment based on the provided payment transaction details.
     *
     * @param paymentTransactionDto The details of the payment transaction to be processed.
     * @return A ResponseEntity containing the processed PaymentTransactionDto and an HTTP status code.
     */
    // POST method to pay rent
    // This method will accept payment transaction details from the client and process the payment
    @PostMapping("/pay")
    public ResponseEntity<PaymentTransactionDto> payRent(@RequestBody PaymentTransactionDto paymentTransactionDto) {
        PaymentTransactionDto response = bookingService.payRent(paymentTransactionDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Verifies the payment signature sent by Razorpay and processes the payment.
     *
     * @param paymentDetails A map containing Razorpay payment details, including order ID, payment ID, and signature.
     * @return A ResponseEntity containing a success message if the payment is verified, or an error message otherwise.
     */
    // POST method to verify the payment signature sent by Razorpay
    // This method will accept the payment details from Razorpay and verify the payment - using the Razorpay SDK
    @PostMapping("/payment-success")
    public ResponseEntity<String> handlePaymentSuccess(@RequestBody Map<String, String> paymentDetails) {
        // Extract Razorpay payment details from the request body
        String razorpayOrderId = paymentDetails.get("razorpay_order_id");
        String razorpayPaymentId = paymentDetails.get("razorpay_payment_id");
        String razorpaySignature = paymentDetails.get("razorpay_signature");
        // Log the values (for debugging)
        //System.out.println("Razorpay Order ID: " + razorpayOrderId);
        //System.out.println("Razorpay Payment ID: " + razorpayPaymentId);
        //System.out.println("Razorpay Signature: " + razorpaySignature);

        // Verify the payment using the Razorpay SDK. If successful, process the payment and return a success response (or) error response
        try {
            // Call the verifyAndProcessPayment method of BookingService with the extracted payment details
            bookingService.verifyAndProcessPayment(razorpayOrderId, razorpayPaymentId, razorpaySignature);
            return new ResponseEntity<>("Payment successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Payment verification failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*
    // POST method to cancel booking - [might add in future]
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable("id") Long userId) {
        String response = bookingService.cancelBooking(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    */

}

