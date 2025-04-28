package com.example.pgbuddy.Dtos;

import com.example.pgbuddy.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

// DTO class for Feedback
@Getter
@Setter
public class FeedbackDto {
    private Long id;
    private String comment;
    private int rating;
    private String userId;
}
