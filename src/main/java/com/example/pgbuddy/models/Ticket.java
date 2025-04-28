package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Ticket class representing a support ticket
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket extends BaseModel {
    // Multiple Tickets can have 1 user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = false)
    private User user;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private PriorityType priority;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    // Multiple Tickets can be assigned to 1 user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to", unique = false)
    private User assignedTo;

    @Enumerated(EnumType.STRING)
    private ResolutionStatus status;
}