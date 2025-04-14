package com.example.pgbuddy.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private String sender;    // Sender's username
    private String receiver;  // Receiver's username
    //private String senderType; // Resident or Admin
    private String content; // Message content
    private String timestamp; // Timestamp of the message
}