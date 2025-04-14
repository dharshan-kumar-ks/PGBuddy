package com.example.pgbuddy.controllers;


import com.example.pgbuddy.Dtos.ChatMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat") // Endpoint for receiving messages
    public void sendMessage(ChatMessageDto message) {
        // Send the message to the specific recipient's queue
        messagingTemplate.convertAndSendToUser(
                message.getReceiver(), // Recipient's username
                "/queue/messages",     // Destination queue
                message                // Message payload
        );
    }
}