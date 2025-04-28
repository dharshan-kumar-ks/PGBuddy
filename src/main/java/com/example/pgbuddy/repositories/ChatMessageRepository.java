package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository interface for ChatMessage entity
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // Find all chat messages for a specific ticket, ordered by creation date
    List<ChatMessage> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
    //List<ChatMessage> findBySenderOrRecipientOrderByTimestampAsc(String sender, String recipient);
}

