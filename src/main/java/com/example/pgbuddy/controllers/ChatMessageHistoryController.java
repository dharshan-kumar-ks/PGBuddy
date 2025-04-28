package com.example.pgbuddy.controllers;


import com.example.pgbuddy.Dtos.ChatMessageDto;
import com.example.pgbuddy.models.ChatMessage;
import com.example.pgbuddy.repositories.ChatMessageRepository;
import com.example.pgbuddy.services.ChatMessageHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling chat message history-related requests.
 * Provides endpoints to retrieve chat history based on ticket ID.
 */
@RestController
@RequestMapping("/api/chat/history")
public class ChatMessageHistoryController {
    private ChatMessageHistoryService chatMessageHistoryService;

    /**
     * Constructor for ChatMessageHistoryController.
     *
     * @param chatMessageHistoryService The service for handling chat message history-related business logic.
     */
    public ChatMessageHistoryController(ChatMessageHistoryService chatMessageHistoryService) {
        this.chatMessageHistoryService = chatMessageHistoryService;
    }

    /**
     * Retrieves the chat message history for a specific ticket.
     *
     * @param ticketId The ID of the ticket for which chat history is to be retrieved.
     * @return A list of ChatMessageDto objects containing the chat history for the specified ticket.
     */
    // GET chat history by ticket ID
    @GetMapping("/{ticketId}")
    public List<ChatMessageDto> getChatHistoryByTicket(@PathVariable Long ticketId) {
        // Call service to get chat history
        return chatMessageHistoryService.getChatHistoryByTicket(ticketId);
    }
}
