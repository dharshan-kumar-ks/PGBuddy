package com.example.pgbuddy.Dtos;

import lombok.*;

import java.util.Date;

// DTO class for Chat Message
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessageDto {
    private Long sender;
    private Long recipient;
    private String content;
    private String timestamp;
    private Long ticketId;
}