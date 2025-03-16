package com.example.pgbuddy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "notices")
public class Notice extends BaseModel {
    private String title;
    private String content;

    // 1 Notice can only have 1 User
    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;

    // createdAt - this is already defined in BaseModel
}
