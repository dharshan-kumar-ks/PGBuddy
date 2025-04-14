package com.example.pgbuddy.filters;

import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.UserRepository;
import com.example.pgbuddy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {

        logger.info(">>> Handshake attempt received.");

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            String token = httpRequest.getParameter("token");

            logger.info(">>> Extracted token from request: {}", token);

            if (token != null && jwtUtil.validateToken(token)) {
                logger.info(">>> Token is valid.");

                String userEmail = jwtUtil.extractEmail(token);
                logger.info(">>> Extracted user email from token: {}", userEmail);

                userRepository.findByEmail(userEmail).ifPresentOrElse(user -> {
                    attributes.put("userId", user.getId());
                    logger.info(">>> User found: {} (ID: {}). Setting attribute.", user.getEmail(), user.getId());
                }, () -> {
                    logger.warn(">>> No user found with email: {}", userEmail);
                });

            } else {
                logger.warn(">>> Token is missing or invalid.");
            }
        } else {
            logger.warn(">>> Request is not an instance of ServletServerHttpRequest.");
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception ex) {
        logger.info(">>> After handshake. Success: {}", ex == null);
        if (ex != null) {
            logger.error(">>> Handshake error occurred: ", ex);
        }
    }
}