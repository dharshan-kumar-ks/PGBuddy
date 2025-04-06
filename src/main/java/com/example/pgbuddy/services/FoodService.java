package com.example.pgbuddy.services;

import com.example.pgbuddy.models.MealTimeType;
import com.example.pgbuddy.models.MealVote;
import com.example.pgbuddy.repositories.FoodRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<MealVote> findFoodByMealDateAndMealTime(String mealDate, String mealTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(mealDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
        }

        MealTimeType mealTimeType;
        try {
            mealTimeType = MealTimeType.valueOf(mealTime.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid meal time type. Please use BREAKFAST, LUNCH, or DINNER.");
        }

        return foodRepository.findByMealDateAndMealTimeType(parsedDate, mealTimeType);
    }
}
