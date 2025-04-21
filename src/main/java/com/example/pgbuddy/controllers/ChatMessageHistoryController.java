package com.example.pgbuddy.controllers;


import com.example.pgbuddy.Dtos.ChatMessageDto;
import com.example.pgbuddy.models.ChatMessage;
import com.example.pgbuddy.repositories.ChatMessageRepository;
import com.example.pgbuddy.services.ChatMessageHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/history")
public class ChatMessageHistoryController {
    private ChatMessageHistoryService chatMessageHistoryService;

    public ChatMessageHistoryController(ChatMessageHistoryService chatMessageHistoryService) {
        this.chatMessageHistoryService = chatMessageHistoryService;
    }

    // GET chat history by ticket ID
    @GetMapping("/{ticketId}")
    public List<ChatMessageDto> getChatHistoryByTicket(@PathVariable Long ticketId) {
        // Call service to get chat history
        return chatMessageHistoryService.getChatHistoryByTicket(ticketId);
    }
}
