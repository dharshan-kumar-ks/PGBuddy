package com.example.pgbuddy.services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static com.razorpay.Utils.bytesToHex;

/**
 * Service class for handling Razorpay payment operations, including order creation and payment signature verification.
 */
@Service
public class RazorpayService {
    private final RazorpayClient razorpayClient;

    /**
     * Constructor for RazorpayService.
     * Initializes the Razorpay client with the provided Key ID and Key Secret.
     *
     * @throws Exception If there is an error during Razorpay client initialization.
     */
    public RazorpayService() throws Exception {
        // Initialize Razorpay client with Key ID and Key Secret in the constructor
        this.razorpayClient = new RazorpayClient("rzp_test_96EzACs3yB6Q9o", "qsxtNCplvXQGxyZzzTfU3KyT");
    }

    /**
     * Creates an order using the Razorpay API and returns the order ID.
     *
     * @param amount   The amount for the order in the specified currency.
     * @param currency The currency for the order (e.g., INR, USD).
     * @param receipt  A unique identifier for the order (e.g., receipt number).
     * @return The ID of the created Razorpay order.
     * @throws Exception If there is an error while creating the order.
     */
    // Method to create an order (via Razorpay API) & return the order ID
    public String createOrder(float amount, String currency, String receipt) throws Exception {
        try {
            // Convert amount to paise - Razorpay API requires the amount in paise
            int amountInPaise = (int) (amount * 100);

            // Create order request payload - with parameters (amount, currency, and receipt)
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", currency);
            orderRequest.put("receipt", receipt);

            // Call Razorpay API to create order
            Order order = razorpayClient.orders.create(orderRequest);

            // Return the order ID
            return order.get("id");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Error while creating Razorpay order: " + e.getMessage(), e);
        }
    }

    /**
     * Verifies the payment signature sent by Razorpay to ensure the payment details have not been tampered with.
     *
     * @param razorpayOrderId   The Razorpay order ID.
     * @param razorpayPaymentId The Razorpay payment ID.
     * @param razorpaySignature The payment signature sent by Razorpay.
     * @return true if the signature is valid, false otherwise.
     */
    // validate the payment signature sent by Razorpay to ensure the payment details have not been tampered in frontend
    public boolean verifyPaymentSignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        // Razorpay generates a signature using HMAC-SHA256, and we need to verify it by recreating the signature on our server using the same algorithm and comparing it with the one sent by Razorpay
        try {
            // Concatenate orderId and paymentId with a pipe separator
            String payload = razorpayOrderId + "|" + razorpayPaymentId;

            // Use the Razorpay secret key
            String secret = "qsxtNCplvXQGxyZzzTfU3KyT";

            // Generate HMAC-SHA256 signature
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256"); // create a secret key spec
            mac.init(secretKeySpec); // initialize the mac with the secret key
            byte[] hash = mac.doFinal(payload.getBytes("UTF-8")); // generate the hash using the payload
            // Convert hash to hexadecimal string
            String generatedSignature = bytesToHex(hash);

            // Compare the generated signature with the Razorpay signature
            //System.out.println("Generated Hash value " + generatedSignature); // uncomment this line to see the generated hash value (debugging purpose)
            //System.out.println("Razorpay Hash value " + razorpaySignature); // uncomment this line to see the Razorpay hash value (debugging purpose)
            return generatedSignature.equals(razorpaySignature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}