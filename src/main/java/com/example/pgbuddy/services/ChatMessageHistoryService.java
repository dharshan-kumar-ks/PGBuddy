package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.ChatMessageDto;
import com.example.pgbuddy.models.ChatMessage;
import com.example.pgbuddy.repositories.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageHistoryService {
    private ChatMessageRepository chatMessageRepository;

    public ChatMessageHistoryService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

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

    return chatMessageDtos;
    }
}
