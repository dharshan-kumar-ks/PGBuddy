package com.example.pgbuddy.configurations;

import com.example.pgbuddy.repositories.UserRepository;
import com.example.pgbuddy.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

// Class to intercept & handle WebSocket handshake and associate user with the session
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    // Logger - used for logging information (for better debugging and tracing)
    private static final Logger logger = LoggerFactory.getLogger(CustomHandshakeHandler.class);

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    // Validates the JWT token and links the WebSocket session to the corresponding user.
    // Takes ServerHttpRequest, WebSocketHandler, and attributes as input & returns a Principal representing the user or null.
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String token = servletRequest.getServletRequest().getParameter("token"); // Extract token from query parameter
            System.out.println("CustomHandshakeHandler is being executed");
            logger.info(">>> Token from query parameter: {}", token);

            // Validate the token and extract userId
            if (token != null && jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.extractUserId(token); // Extract userId from token
                if (userRepository.existsById(userId)) { // Ensure user exists
                    logger.info("Associating WebSocket session with userId: {}", userId);
                    return () -> String.valueOf(userId); // Return a Principal with the userId
                } else {
                    logger.warn("User not found for userId: {}", userId);
                }
            } else {
                logger.warn("Invalid or missing JWT token");
            }
        }
        // If token is invalid or user not found, return null
        return super.determineUser(request, wsHandler, attributes);
    }
}