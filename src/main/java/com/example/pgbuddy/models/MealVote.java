package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "meal_votes")
public class MealVote extends BaseModel {
    // 1 User can have only 1 MealVote
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 1 MealVote can have only 1 Meal
    @OneToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @Temporal(TemporalType.DATE)
    private Date MealDate;
}
