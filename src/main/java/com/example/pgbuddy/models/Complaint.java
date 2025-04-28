package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

// Complaint class representing a complaint made by a user
@Data
@Entity
@Table(name = "complaints")
public class Complaint extends BaseModel {
    // Multiple Complaints can have 1 User (M:1)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String description;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus ComplaintStatus; // Enum: PENDING, RESOLVED, IN_PROGRESS

    /*
    // messages List (1:M)
    // photoURL List (1:M)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "complaint_id")
    private List<Message> messages = new ArrayList<>();

    @ElementCollection
    private List<String> photoUrls = new ArrayList<>();
     */
}




