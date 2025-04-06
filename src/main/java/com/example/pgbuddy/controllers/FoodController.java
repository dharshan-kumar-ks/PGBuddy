package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.VoteRequestDto;
import com.example.pgbuddy.models.MealVote;
import com.example.pgbuddy.services.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // POST method to increment the counter of the meal when the user votes
    @PostMapping("/vote")
    public ResponseEntity<String> voteForMeal(@RequestBody VoteRequestDto voteRequestDto) {
        boolean success = foodService.voteForMeal(voteRequestDto.getMealVoteId(), voteRequestDto.getUserId());
        if (success) {
            return ResponseEntity.ok("Vote counted successfully.");
        } else {
            return ResponseEntity.badRequest().body("User has already voted for this meal.");
        }
    }

    // GET method to fetch the count of votes for a specific meal
    @GetMapping("/vote/count/{mealVoteId}")
    public ResponseEntity<Integer> getVoteCount(@PathVariable("mealVoteId") Long mealVoteId) {
        int voteCount = foodService.getVoteCount(mealVoteId);
        return ResponseEntity.ok(voteCount);
    }

}
