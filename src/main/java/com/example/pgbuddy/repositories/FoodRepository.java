package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.MealTimeType;
import com.example.pgbuddy.models.MealVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<MealVote, Long> {
    List<MealVote> findByMealDateAndMealTimeType(Date mealDate, MealTimeType mealTimeType);
}


