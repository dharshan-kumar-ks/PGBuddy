package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.BookingDto;
import com.example.pgbuddy.Dtos.PaymentTransactionDto;
import com.example.pgbuddy.exceptions.ResourceNotFoundException;
import com.example.pgbuddy.models.Booking;
import com.example.pgbuddy.models.Payment;
import com.example.pgbuddy.models.PaymentStatus;
import com.example.pgbuddy.repositories.BookingRepository;
import com.example.pgbuddy.repositories.PaymentRepository;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service class for handling booking-related operations.
 */
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;

    /**
     * Constructor for BookingService.
     *
     * @param bookingRepository Repository for accessing booking data.
     * @param paymentRepository Repository for accessing payment data.
     * @param razorpayService Service for interacting with Razorpay API.
     */
    public BookingService(BookingRepository bookingRepository, PaymentRepository paymentRepository, RazorpayService razorpayService) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.razorpayService = razorpayService;
    }

    /**
     * Retrieves booking details for a specific user.
     *
     * @param userId The ID of the user whose booking details are to be retrieved.
     * @return A BookingDto containing the booking details.
     * @throws ResourceNotFoundException If no booking is found for the given user ID.
     */
    // GET method to get booking details for the user
    public BookingDto getBookingDetails(Long userId) {
        // Fetch booking by userId from the database
        Booking booking = bookingRepository.findByUserId(userId);
        // Check if booking exists; else throw an exception
        if (booking == null) {
            throw new ResourceNotFoundException("Booking not found for userId: " + userId);
        }

        // Convert Booking entity to BookingDto:-
        // Create a new BookingDto object and set its properties
        BookingDto bookingDto = new BookingDto();
        bookingDto.setBookingId(booking.getId());
        bookingDto.setTenureStartDate(booking.getTenureStartDate());
        bookingDto.setTenureEndDate(booking.getTenureEndDate());
        bookingDto.setMonthlyRent(booking.getMonthlyRent());
        bookingDto.setDiscountPercent(booking.getDiscountPercent());
        bookingDto.setTotalAmountPaidTillDate(booking.getTotalAmountPaidTillDate());
        bookingDto.setDuesRemaining(booking.getDuesRemaining());
        bookingDto.setUserId(booking.getUser().getId());

        return bookingDto; // Return the BookingDto object
    }

    /**
     * Retrieves a list of payment transaction details for a specific user.
     *
     * @param userId The ID of the user whose payment transactions are to be retrieved.
     * @return A list of PaymentTransactionDto containing the payment transaction details.
     * @throws ResourceNotFoundException If no booking is found for the given user ID.
     */
    // GET method to get the list of payment transaction details of user
    public List<PaymentTransactionDto> getPaymentTransactions(Long userId) {
        // Fetch booking by userId from the database
        Booking booking = bookingRepository.findByUserId(userId);
        // Check if booking exists; else throw an exception
        if (booking == null) {
            throw new ResourceNotFoundException("Booking not found for userId: " + userId);
        }

        // Extract the List of Payment objects from the Booking entity
        List<PaymentTransactionDto> paymentTransactions = booking.getPayments() // Get the list of payments from the booking
                .stream() // Convert the list to a stream (its a better way to process each payment)
                .map(payment -> {
                    // Create a new PaymentTransactionDto object and set its properties
                    PaymentTransactionDto paymentTransactionDto = new PaymentTransactionDto();
                    paymentTransactionDto.setTransactionId(payment.getId().toString());
                    paymentTransactionDto.setAmount(payment.getAmount());
                    paymentTransactionDto.setTransactionDate(payment.getCreatedAt() != null ? payment.getCreatedAt().toString() : "N/A");
                    paymentTransactionDto.setPaymentStatus(payment.getPaymentStatus().toString());
                    paymentTransactionDto.setUserId(userId);
                    return paymentTransactionDto; // Return the PaymentTransactionDto object
                })
                .toList(); // Collect the stream back to a list
        return paymentTransactions; // Return the list of payment transactions
    }

    /**
     * Processes a rent payment for a user.
     *
     * @param paymentTransactionDto The payment transaction details provided by the user.
     * @return A PaymentTransactionDto containing the transaction details and Razorpay order ID.
     * @throws ResourceNotFoundException If no booking is found for the given user ID.
     * @throws RuntimeException If the Razorpay order creation fails.
     */
    // POST method to pay rent
    public PaymentTransactionDto payRent(PaymentTransactionDto paymentTransactionDto) {
        // Fetch booking by userId from the database
        Booking booking = bookingRepository.findByUserId(paymentTransactionDto.getUserId());
        // Check if booking exists; else throw an exception
        if (booking == null) {
            throw new ResourceNotFoundException("Booking not found for userId: " + paymentTransactionDto.getUserId());
        }

        try {
            // Generate Razorpay order
            String receipt = "receipt_" + System.currentTimeMillis();
            // Method to create an order (via Razorpay API) & return the order ID
            String orderId = razorpayService.createOrder(paymentTransactionDto.getAmount(), "INR", receipt);

            // Create a new Payment object with PROCESSING status. Set the other attributes like user, amount, and booking details
            Payment payment = new Payment();
            payment.setUser(booking.getUser());
            payment.setAmount(paymentTransactionDto.getAmount());
            payment.setPaymentStatus(PaymentStatus.PROCESSING); // Set status as PROCESSING
            payment.setCreatedAt(LocalDateTime.now()); // Set the current date and time as the created date
            payment.setBooking(booking);
            payment.setRazorpayOrderId(orderId); // Save Razorpay order ID

            // Save the payment to the database
            payment = paymentRepository.save(payment);

            // Return the Razorpay order ID and other details to the frontend
            paymentTransactionDto.setTransactionId(orderId);
            paymentTransactionDto.setPaymentStatus(payment.getPaymentStatus().toString());
            return paymentTransactionDto; // Return the PaymentTransactionDto object with transaction details

        } catch (Exception e) {
            // Log the error details
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }

    /**
     * Verifies and processes a payment based on the Razorpay signature.
     *
     * @param razorpayOrderId The Razorpay order ID.
     * @param razorpayPaymentId The Razorpay payment ID.
     * @param razorpaySignature The Razorpay signature for verification.
     * @throws RuntimeException If payment verification fails or payment is not found.
     */
    // POST method to verify the payment signature sent by Razorpay
    public void verifyAndProcessPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        // Uncomment the below lines to log the values for debugging :-
        //System.out.println("Razorpay Order ID: " + razorpayOrderId);
        //System.out.println("Razorpay Payment ID: " + razorpayPaymentId);
        //System.out.println("Razorpay Signature: " + razorpaySignature);

        // Verify the payment signature - using the Razorpay SDK
        boolean isValid = razorpayService.verifyPaymentSignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);

        // Fetch the payment details from the database using the Razorpay order ID
        Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId);

        // If the signature is invalid, update the payment status to FAILED
        if (!isValid) {
            // Update payment status to FAILED if the signature is invalid
            if (payment != null) {
                payment.setPaymentStatus(PaymentStatus.FAILED); // Update payment status to FAILED
                paymentRepository.save(payment); // Save the updated payment status to the database
            }
            throw new RuntimeException("Payment verification failed"); // Throw an exception if verification fails
        }

        // If the payment object is not null, update the payment status to SUCCESS
        if (payment != null) {
            payment.setPaymentStatus(PaymentStatus.SUCCESS); // Update payment status to SUCCESS
            paymentRepository.save(payment); // Save the updated payment status to the database

            // Update the booking with the payment details
            Booking booking = payment.getBooking();
            booking.setTotalAmountPaidTillDate(booking.getTotalAmountPaidTillDate() + payment.getAmount()); // Update total amount paid
            booking.setDuesRemaining(booking.getDuesRemaining() - payment.getAmount()); // Update dues remaining
            // Add the payment to the booking
            booking.getPayments().add(payment);
            bookingRepository.save(booking); // Save the updated booking to the database
        } else {
            // Handle the case where payment is not found
            throw new RuntimeException("Payment not found for Razorpay order ID: " + razorpayOrderId);
        }
    }

}





