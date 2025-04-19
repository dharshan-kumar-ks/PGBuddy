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

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;

    public BookingService(BookingRepository bookingRepository, PaymentRepository paymentRepository, RazorpayService razorpayService) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.razorpayService = razorpayService;
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

    // POST method to pay rent
    public PaymentTransactionDto payRent(PaymentTransactionDto paymentTransactionDto) {
        // Fetch booking by userId
        Booking booking = bookingRepository.findByUserId(paymentTransactionDto.getUserId());
        if (booking == null) {
            throw new ResourceNotFoundException("Booking not found for userId: " + paymentTransactionDto.getUserId());
        }

        try {
            // Generate Razorpay order
            String receipt = "receipt_" + System.currentTimeMillis();
            // Method to create an order (via Razorpay API) & return the order ID
            String orderId = razorpayService.createOrder(paymentTransactionDto.getAmount(), "INR", receipt);

            // Create a new Payment object with PROCESSING status
            Payment payment = new Payment();
            payment.setUser(booking.getUser());
            payment.setAmount(paymentTransactionDto.getAmount());
            payment.setPaymentStatus(PaymentStatus.PROCESSING); // Set status as PROCESSING
            payment.setCreatedAt(LocalDateTime.now());
            payment.setBooking(booking);
            payment.setRazorpayOrderId(orderId); // Save Razorpay order ID

            // Save the payment to the database
            payment = paymentRepository.save(payment);

            // Update the booking with the payment details
            booking.setTotalAmountPaidTillDate(booking.getTotalAmountPaidTillDate() + payment.getAmount());
            booking.setDuesRemaining(booking.getDuesRemaining() - payment.getAmount());
            // Add the payment to the booking
            booking.getPayments().add(payment);
            bookingRepository.save(booking);

            // Return the Razorpay order ID and other details to the frontend
            paymentTransactionDto.setTransactionId(orderId);
            paymentTransactionDto.setPaymentStatus(payment.getPaymentStatus().toString());
            return paymentTransactionDto;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }

    // POST method to verify the payment signature sent by Razorpay
    public void verifyAndProcessPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        // Log the values for debugging
        //System.out.println("Razorpay Order ID: " + razorpayOrderId);
        //System.out.println("Razorpay Payment ID: " + razorpayPaymentId);
        //System.out.println("Razorpay Signature: " + razorpaySignature);

        // Verify the payment signature
        boolean isValid = razorpayService.verifyPaymentSignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);
        Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId);
        if (!isValid) {
            // Update payment status to FAILED if the signature is invalid
            if (payment != null) {
                payment.setPaymentStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
            }
            throw new RuntimeException("Payment verification failed");
        }

        // Update payment status to SUCCESS in the database
        if (payment != null) {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
        } else {
            // Handle the case where payment is not found
            throw new RuntimeException("Payment not found for Razorpay order ID: " + razorpayOrderId);
        }
    }

}





