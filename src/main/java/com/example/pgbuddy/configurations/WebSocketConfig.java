package com.example.pgbuddy.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

// Class to configure WebSocket communication in the application.
// It sets up the message broker, STOMP endpoints, and custom handshake handling for user authentication.
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // Logger - used for logging information (for better debugging and tracing)
    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Bean
    public CustomHandshakeHandler customHandshakeHandler() {
        return new CustomHandshakeHandler();
    }


    // Configures the message broker - for handling messaging destinations in the WebSocket application.
    // Messages sent to destinations starting with /app & /user will be routed to message-handling methods annotated with @MessageMapping in Chat controllers.
    // Takes a MessageBrokerRegistry as input and returns void.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enables a simple in-memory message broker for handling messages.
        config.enableSimpleBroker("/topic", "/queue");
        // Configures the application destination prefixes for sending messages.
        config.setApplicationDestinationPrefixes("/app"); // Prefix for application destinations
        config.setUserDestinationPrefix("/user"); // Prefix for user destinations
        logger.info("Message broker configured with destinations: /topic, /queue, /app, /user");
    }

    // Registers a STOMP endpoint for WebSocket communication with SockJS fallback.
    // Takes a StompEndpointRegistry as input and returns void.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        logger.info("Registering STOMP endpoint: /chat with allowed origin http://localhost:5173");
        registry.addEndpoint("/chat") // Endpoint for WebSocket connection
                .setAllowedOrigins("http://localhost:5173") // Allow requests from this origin
                .setAllowedOrigins("https://pg-buddy-front-end.vercel.app") // Allow requests from this origin - vercel frontend
                .setHandshakeHandler(customHandshakeHandler()) // Use custom handshake handler
                .withSockJS(); // Enable SockJS fallback
    }
}