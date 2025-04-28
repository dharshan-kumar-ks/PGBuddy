package com.example.pgbuddy.models;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

// Model class for Message details
@Data
@Entity
@Table(name = "messages")
public class Message extends BaseModel {
    private String content;

    // 1 User can have only 1 Message
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
