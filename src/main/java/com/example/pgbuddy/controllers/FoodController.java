package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.VoteRequestDto;
import com.example.pgbuddy.models.MealVote;
import com.example.pgbuddy.services.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling food-related requests.
 * Provides endpoints for searching food items, voting for meals, and retrieving vote counts.
 */
@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;

    /**
     * Controller for handling food-related requests.
     * Provides endpoints for searching food items, voting for meals, and retrieving vote counts.
     */
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * Retrieves food items based on meal date and meal time.
     *
     * @param mealDate The date of the meal (format: yyyy-MM-dd).
     * @param mealTime The time of the meal (e.g., breakfast, lunch, dinner).
     * @return A ResponseEntity containing a list of MealVote objects for the specified date and time.
     */
    // GET method to fetch food items based on MealDate & MealTime
    // This method will return a list of food items available for the specified date and time
    @GetMapping("/search")
    public ResponseEntity<List<MealVote>> getFoodByMealDateAndMealTime(
            @RequestParam("mealDate") String mealDate,
            @RequestParam("mealTime") String mealTime) {
        // Call the service to get food items based on meal date and time
        List<MealVote> mealVotes = foodService.findFoodByMealDateAndMealTime(mealDate, mealTime);
        return ResponseEntity.ok(mealVotes);
    }

    /**
     * Increments the vote count for a specific meal.
     *
     * @param voteRequestDto The request body containing the meal vote ID and user ID.
     * @return A ResponseEntity containing a success message if the vote is counted, or an error message if the user has already voted.
     */
    // POST method to increment the counter of the meal when the user votes
    // This method will accept the meal vote ID and user ID from the client and increment the vote count for that meal for that user
    @PostMapping("/vote")
    public ResponseEntity<String> voteForMeal(@RequestBody VoteRequestDto voteRequestDto) {
        // Call the service to vote for a meal
        boolean success = foodService.voteForMeal(voteRequestDto.getMealVoteId(), voteRequestDto.getUserId());
        // Return success or failure response
        if (success) {
            return ResponseEntity.ok("Vote counted successfully.");
        } else {
            return ResponseEntity.badRequest().body("User has already voted for this meal.");
        }
    }

    /**
     * Retrieves the vote count for a specific meal.
     *
     * @param mealVoteId The ID of the meal vote.
     * @return A ResponseEntity containing the vote count for the specified meal.
     */
    // GET method to fetch the count of votes for a specific meal
    @GetMapping("/vote/count/{mealVoteId}")
    public ResponseEntity<Integer> getVoteCount(@PathVariable("mealVoteId") Long mealVoteId) {
        // Call the service to get the vote count for the specified meal
        int voteCount = foodService.getVoteCount(mealVoteId);
        return ResponseEntity.ok(voteCount);
    }

}
