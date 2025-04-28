package com.example.pgbuddy.filters;

import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.UserRepository;
import com.example.pgbuddy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

// Interceptor for handling JWT authentication in WebSocket handshakes
@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    // Logger for logging information (for better debugging and tracing)
    private static final Logger logger = LoggerFactory.getLogger(JwtHandshakeInterceptor.class);

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtHandshakeInterceptor(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    // This method is executed before a WebSocket handshake is completed.
    // It validates the JWT token (if provided):
    // If the token is valid, the user details are extracted and stored in the attributes map & allows the connection.
    // If the token is missing or invalid, the connection is allowed (but without user authentication - no user details are set) => [we can later code to refuse the connection]
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info(">>> Handshake attempt received.");

        String token = null; // Initialize token to null

        // Check if the request is an instance of ServletServerHttpRequest
        if (request instanceof ServletServerHttpRequest servletRequest) {
            // Get the HttpServletRequest from the ServerHttpRequest
            HttpServletRequest req = servletRequest.getServletRequest();
            // Extract the Authorization header from the request (for SockJS)
            String authHeader = req.getHeader("Authorization");

            // Check if the Authorization header is present and starts with "Bearer"; else fallback to query parameter
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                logger.info(">>> Token extracted from Authorization header: {}", token);
            } else {
                // Fallback to query parameter
                token = req.getParameter("token");
                logger.info(">>> Token extracted from query parameter: {}", token);
            }
        }

        // Validate the token
        if (token == null || !jwtUtil.validateToken(token)) {
            logger.warn(">>> Token is missing or invalid. Connection allowed but unauthenticated.");
            return true; // Allow connection but don’t set user details
        }

        // Extract user email from the token
        String email = jwtUtil.extractEmail(token);
        logger.info(">>> Extracted user email from token: {}", email);
        // Find the user by email
        User user = userRepository.findByEmail(email).orElse(null);
        // If the user is not found, log a warning and allow connection without setting user details
        if (user == null) {
            logger.warn(">>> No user found for token.");
            return true;
        }

        // Set both userId and email for flexibility
        attributes.put("userId", user.getId());
        attributes.put("email", email);
        logger.info(">>> User found: {} (ID: {}). Setting attributes.", email, user.getId());

        return true; // Allow the handshake to proceed
    }

    // This method is called after the WebSocket handshake is completed - for now we do nothing (just log success or failure error)
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        logger.info(">>> After handshake. Success: {}", ex == null);
        // Log the exception if it occurred
        if (ex != null) {
            logger.error(">>> Handshake error occurred: ", ex);
        }
    }
}