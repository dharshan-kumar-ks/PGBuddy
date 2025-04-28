package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.ChatMessageDto;
import com.example.pgbuddy.models.ChatMessage;
import com.example.pgbuddy.repositories.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling chat message history operations.
 */
@Service
public class ChatMessageHistoryService {
    private ChatMessageRepository chatMessageRepository;

    /**
     * Constructor for ChatMessageHistoryService.
     *
     * @param chatMessageRepository Repository for accessing chat message data.
     */
    public ChatMessageHistoryService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    /**
     * Retrieves the chat history for a specific ticket, ordered by creation time.
     *
     * @param ticketId The ID of the ticket for which the chat history is to be retrieved.
     * @return A list of ChatMessageDto objects representing the chat messages.
     */
    // GET chat history by ticket ID
    public List<ChatMessageDto> getChatHistoryByTicket(Long ticketId) {
        // Call repository to get chat history
        List<ChatMessage> chatMessages = chatMessageRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);

        // Convert to DTOs
        List<ChatMessageDto> chatMessageDtos = chatMessages.stream()
                .map(chatMessage -> new ChatMessageDto(
                        chatMessage.getSender().getId(),
                        chatMessage.getRecipient().getId(),
                        chatMessage.getContent(),
                        chatMessage.getCreatedAt().toString(),
                        chatMessage.getTicket().getId()))
                .toList();

    return chatMessageDtos; // Return the list of chat message DTOs
    }
}
