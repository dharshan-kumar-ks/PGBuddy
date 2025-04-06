package com.example.pgbuddy.Dtos;

import com.example.pgbuddy.models.Meal;
import com.example.pgbuddy.models.MealDayType;
import com.example.pgbuddy.models.MealTimeType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FoodDto {
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @Enumerated(EnumType.STRING)
    private MealTimeType mealTimeType; // BREAKFAST / LUNCH / DINNER

    @Temporal(TemporalType.DATE)
    private Date mealDate;

    @Enumerated(EnumType.STRING)
    private MealDayType mealDayType; // MONDAY / TUESDAY / WEDNESDAY etc...
}
