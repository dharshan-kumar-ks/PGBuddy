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

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDto chatMessageDto) {
        chatMessageDto.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Save the message to the database
        ChatMessage chatMessage = new ChatMessage();
        User sender = userRepository.findById(chatMessageDto.getSender()).orElse(null);
        chatMessage.setSender(sender);
        User recipient = userRepository.findById(chatMessageDto.getRecipient()).orElse(null);
        chatMessage.setRecipient(recipient);
        chatMessage.setContent(chatMessageDto.getContent());
        chatMessage.setCreatedAt(LocalDateTime.now());
        Ticket ticket = ticketRepository.findById(chatMessageDto.getTicketId()).orElse(null);
        chatMessage.setTicket(ticket); // Get ticket from ticket ID & set it
        chatMessageRepository.save(chatMessage);

        // Send message to recipient
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageDto.getRecipient()),
                "/queue/messages",
                chatMessageDto
        );

        // Send message back to sender
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageDto.getSender()),
                "/queue/messages",
                chatMessageDto
        );
    }
}

