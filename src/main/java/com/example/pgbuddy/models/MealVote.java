package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "meal_votes")
public class MealVote extends BaseModel {
    /*
    // 1 User can have only 1 MealVote
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    */

    // Multiple MealVote can have same Meal -> M : 1
    // in other words, 1 Meal can be part of Multiple MealVotes
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @Enumerated(EnumType.STRING)
    private MealTimeType mealTimeType; // BREAKFAST / LUNCH / DINNER

    @Temporal(TemporalType.DATE)
    private Date mealDate;

    @Enumerated(EnumType.STRING)
    private MealDayType mealDayType; // MONDAY / TUESDAY / WEDNESDAY etc...

    private int voteCount; // Counter for votes
}
