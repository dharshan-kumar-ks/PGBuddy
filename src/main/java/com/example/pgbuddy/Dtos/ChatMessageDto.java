package com.example.pgbuddy.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private String sender;
    private String content;
    private String type; // JOIN, LEAVE, CHAT
}