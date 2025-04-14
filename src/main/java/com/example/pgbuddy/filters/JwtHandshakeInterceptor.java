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

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtHandshakeInterceptor.class);

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtHandshakeInterceptor(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info(">>> Handshake attempt received.");

        String token = null;
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest req = servletRequest.getServletRequest();
            // Check Authorization header first (preferred for SockJS)
            String authHeader = req.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                logger.info(">>> Token extracted from Authorization header: {}", token);
            } else {
                // Fallback to query parameter
                token = req.getParameter("token");
                logger.info(">>> Token extracted from query parameter: {}", token);
            }
        }

        if (token == null || !jwtUtil.validateToken(token)) {
            logger.warn(">>> Token is missing or invalid. Connection allowed but unauthenticated.");
            return true; // Allow connection but don’t set user details
        }

        String email = jwtUtil.extractEmail(token);
        logger.info(">>> Extracted user email from token: {}", email);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            logger.warn(">>> No user found for token.");
            return true;
        }

        // Set both userId and email for flexibility
        attributes.put("userId", user.getId());
        attributes.put("email", email);
        logger.info(">>> User found: {} (ID: {}). Setting attributes.", email, user.getId());

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        logger.info(">>> After handshake. Success: {}", ex == null);
        if (ex != null) {
            logger.error(">>> Handshake error occurred: ", ex);
        }
    }
}