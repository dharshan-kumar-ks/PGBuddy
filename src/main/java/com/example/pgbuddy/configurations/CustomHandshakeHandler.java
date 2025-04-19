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


public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomHandshakeHandler.class);

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String token = servletRequest.getServletRequest().getParameter("token"); // Extract token from query parameter
            System.out.println("CustomHandshakeHandler is being executed");
            logger.info(">>> Token from query parameter: {}", token);
            if (token == null) {
                token = servletRequest.getServletRequest().getHeader("Authorization"); // Or from headers
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7); // Remove "Bearer " prefix
                }
            }

            if (token != null && jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.extractUserId(token); // Extract userId from token
                // Extract username from userRepo Layer
                String username = userRepository.findById(userId)
                        .map(user -> user.getName()) // Assuming your User entity has a getUsername() method
                        .orElse(null); // Handle the case where the user is not found

                if (username != null) {
                    logger.info("Associating WebSocket session with username: {}", username);
                    return () -> username; // Return a Principal with the username
                } else {
                    logger.warn("Failed to extract username from token");
                }
            } else {
                logger.warn("Invalid or missing JWT token");
            }
        }
        return super.determineUser(request, wsHandler, attributes);
    }
}