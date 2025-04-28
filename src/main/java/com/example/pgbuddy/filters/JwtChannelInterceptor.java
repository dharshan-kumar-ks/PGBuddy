package com.example.pgbuddy.filters;

import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.UserRepository;
import com.example.pgbuddy.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

// Interceptor for handling JWT authentication in STOMP messages
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {
    // Logger for logging information (for better debugging and tracing)
    private static final Logger logger = LoggerFactory.getLogger(JwtChannelInterceptor.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // Method to intercept messages & validate token - before they are sent to MessageChannel in MessageBrooker
    // This method is called before the 'message' is sent to the 'channel'
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // Extract the STOMP headers from the message
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        // If the message is a STOMP CONNECT command, extract the Authorization header
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            logger.info("CONNECT headers: {}", accessor.toNativeHeaderMap());

            // Check if the Authorization header is present and starts with "Bearer "
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                logger.info(">>> Token from STOMP CONNECT: {}", token);

                // Validate the token & extract user information
                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.extractEmail(token);
                    User user = userRepository.findByEmail(email).orElse(null);

                    // If the user is found, set the user in the StompHeaderAccessor; else log a warning
                    if (user != null) {
                        accessor.setUser(() -> user.getEmail());
                        logger.info("Authenticated STOMP user: {}", email);
                    } else {
                        logger.warn("User not found for email: {}", email);
                    }
                } else {
                    logger.warn("Invalid JWT in CONNECT");
                }
            } else {
                logger.warn("No Authorization header in CONNECT");
            }
        }
        return message;
    }
}