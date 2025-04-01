package com.example.pgbuddy.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDto {
    private Long id;
    private Long userId; // User ID of the creator
    private String title;
    private String description;
    private String priority;
    private String ticketType;
    //private String category;
    private Long assignedTo; // Can be null if not immediately assigned
    private String status = "PENDING";  // Default status
}
