package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends BaseModel {
    // Multiple chats can be part of 1 User (M:1)
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // Multiple chats can be part of 1 User (M:1)
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // Multiple chats can be part of 1 Ticket (M:1)
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket; // for ticket association

    //@Column(nullable = false)
    //private LocalDateTime timestamp;
}
