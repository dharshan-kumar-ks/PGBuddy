package com.example.pgbuddy.services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {
    private final RazorpayClient razorpayClient;

    public RazorpayService() throws Exception {
        // Initialize Razorpay client with Key ID and Key Secret
        this.razorpayClient = new RazorpayClient("rzp_test_96EzACs3yB6Q9o", "qsxtNCplvXQGxyZzzTffffff");
    }

    public String createOrder(float amount, String currency, String receipt) throws Exception {
        // Convert amount to paise (Razorpay expects amount in paise)
        int amountInPaise = (int) (amount * 100);

        // Create order request payload
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", receipt);

        // Create order using Razorpay API
        //Order order = razorpayClient.Orders.create(orderRequest);

        // Return the order ID
        return order.get("id");
    }
}