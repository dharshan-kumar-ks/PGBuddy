package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "meals")
// Multiple users can have 1 Meal
public class Meal extends BaseModel {
    private String mealName;

    @Enumerated(EnumType.STRING)
    private MealType mealType; // VEG / NONVEG

    @ElementCollection
    private List<String> MealItems;
}
