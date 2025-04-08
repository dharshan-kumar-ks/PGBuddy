package com.example.pgbuddy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseModel {
    private String comment;
    private int rating;

    // Multiple feedbacks can have 1 User (M:1)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
