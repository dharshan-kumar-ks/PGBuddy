package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "meals")
// Multiple users can have 1 Meal
public class Meal extends BaseModel {
    //private String mealName;
    //private String mealAddOn;
    private String mealImageUrl;

    @Enumerated(EnumType.STRING)
    private MealType mealType; // VEG / NONVEG

    //  JPA will create a separate table to store the elements of the mealItems list.
    //  This table will have a foreign key reference to the Meal entity.
    @ElementCollection
    @CollectionTable(name = "meal_items", joinColumns = @JoinColumn(name = "meal_id"))
    private List<String> mealItems;
    @ElementCollection
    @CollectionTable(name = "meal_add_on", joinColumns = @JoinColumn(name = "meal_id"))
    private List<String> mealAddOn;
}
