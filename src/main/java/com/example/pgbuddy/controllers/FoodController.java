package com.example.pgbuddy.controllers;

import com.example.pgbuddy.models.MealVote;
import com.example.pgbuddy.services.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // GET method to fetch food items based on MealDate & MealTime
    @GetMapping("/search")
    public ResponseEntity<List<MealVote>> getFoodByMealDateAndMealTime(
            @RequestParam("mealDate") String mealDate,
            @RequestParam("mealTime") String mealTime) {
        List<MealVote> mealVotes = foodService.findFoodByMealDateAndMealTime(mealDate, mealTime);
        return ResponseEntity.ok(mealVotes);
    }
}
