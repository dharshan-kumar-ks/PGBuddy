package com.example.pgbuddy.controllers;


import com.example.pgbuddy.Dtos.ChatMessageDto;
import com.example.pgbuddy.models.ChatMessage;
import com.example.pgbuddy.models.Ticket;
import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.ChatMessageRepository;
import com.example.pgbuddy.repositories.TicketRepository;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Controller for handling chat-related requests and operations.
 * This includes processing and saving chat messages, as well as sending messages to users.
 */
@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Handles incoming chat messages sent to the "/chat.sendMessage" queue.
     * Processes the message, saves it to the database, and sends it to both the sender and recipient.
     *
     * @param chatMessageDto The DTO containing the details of the chat message.
     */
    // Method to handle incoming chat messages - get & process all messages received in "/chat.sendMessage" queue
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDto chatMessageDto) {
        // Set the timestamp for the message
        chatMessageDto.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Get the sender and recipient IDs from the DTO & set them in the message
        // Save this message to the database
        ChatMessage chatMessage = new ChatMessage();
        User sender = userRepository.findById(chatMessageDto.getSender()).orElse(null); // Get sender from user ID & set it in the message
        chatMessage.setSender(sender);
        User recipient = userRepository.findById(chatMessageDto.getRecipient()).orElse(null); // Get recipient from user ID & set it in the message
        chatMessage.setRecipient(recipient);
        chatMessage.setContent(chatMessageDto.getContent());
        chatMessage.setCreatedAt(LocalDateTime.now());
        Ticket ticket = ticketRepository.findById(chatMessageDto.getTicketId()).orElse(null);
        chatMessage.setTicket(ticket); // Get ticket from ticket ID & set it
        chatMessageRepository.save(chatMessage);

        // Send message to recipient - to /messages queue
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageDto.getRecipient()),
                "/queue/messages",
                chatMessageDto
        );

        // Send message back to sender - to /messages queue
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageDto.getSender()),
                "/queue/messages",
                chatMessageDto
        );
    }
}

