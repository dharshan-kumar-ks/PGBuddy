package com.example.pgbuddy.services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static com.razorpay.Utils.bytesToHex;

@Service
public class RazorpayService {
    private final RazorpayClient razorpayClient;

    public RazorpayService() throws Exception {
        // Initialize Razorpay client with Key ID and Key Secret in the constructor
        this.razorpayClient = new RazorpayClient("rzp_test_96EzACs3yB6Q9o", "qsxtNCplvXQGxyZzzTfU3KyT");
    }

    // Method to create an order (via Razorpay API) & return the order ID
    public String createOrder(float amount, String currency, String receipt) throws Exception {
        try {
            // Convert amount to paise
            int amountInPaise = (int) (amount * 100);

            // Create order request payload
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
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(payload.getBytes("UTF-8"));
            // Convert hash to hexadecimal string
            String generatedSignature = bytesToHex(hash);

            // Compare the generated signature with the Razorpay signature
            //System.out.println("Generated Hash value " + generatedSignature);
            //System.out.println("Razorpay Hash value " + razorpaySignature);
            return generatedSignature.equals(razorpaySignature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}