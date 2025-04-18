package com.example.pgbuddy.Dtos;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessageDto {
    private String sender;
    private String recipient;
    private String content;
    private String timestamp;
    private Long ticketId;
}