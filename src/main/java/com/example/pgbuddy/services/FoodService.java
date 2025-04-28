package com.example.pgbuddy.services;

import com.example.pgbuddy.models.MealTimeType;
import com.example.pgbuddy.models.MealVote;
import com.example.pgbuddy.repositories.FoodRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for handling food-related operations, including fetching meals, voting for meals, and retrieving vote counts.
 */
@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final Set<String> userVotes = ConcurrentHashMap.newKeySet(); // stores (userId, mealVoteId) pair to track votes

    /**
     * Constructor for FoodService.
     *
     * @param foodRepository Repository for accessing food-related data.
     */
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    /**
     * Retrieves food items based on the provided meal date and meal time.
     *
     * @param mealDate The date of the meal in the format "yyyy-MM-dd".
     * @param mealTime The time of the meal (e.g., BREAKFAST, LUNCH, DINNER).
     * @return A list of MealVote objects representing the food items for the specified date and time.
     * @throws IllegalArgumentException If the date format is invalid or the meal time type is invalid.
     */
    // GET method to fetch food items based on MealDate & MealTime
    public List<MealVote> findFoodByMealDateAndMealTime(String mealDate, String mealTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // defining the date format (as stored in DB table)
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(mealDate); // parsing the date string according to the defined date format in DB
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
        }

        MealTimeType mealTimeType;
        try {
            mealTimeType = MealTimeType.valueOf(mealTime.toUpperCase()); // converting the meal time string to enum
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid meal time type. Please use BREAKFAST, LUNCH, or DINNER.");
        }

        // Fetch food items from the repository based on the parsed date and meal time type
        return foodRepository.findByMealDateAndMealTimeType(parsedDate, mealTimeType);
    }


    /**
     * Allows a user to vote for a specific meal. Increments the vote count for the selected meal
     * and decrements the vote count for any previously selected meal for the same day and time.
     *
     * @param mealVoteId The ID of the meal to vote for.
     * @param userId The ID of the user voting for the meal.
     * @return true if the vote was successfully cast, false if the user has already voted for the meal.
     * @throws IllegalArgumentException If the mealVoteId is invalid.
     */
    // POST method to increment the counter of the meal when the user votes
    // & decrement the other 2 counters
    public boolean voteForMeal(Long mealVoteId, Long userId) {
        String userVoteKey = userId + "-" + mealVoteId; // form a unique key for the user and mealVoteId
        // Check if the user has already voted for this meal
        if (userVotes.contains(userVoteKey)) {
            return false; // User has already voted for this meal
        }

        // If not, proceed to increment the vote count in MealVote table (for the corresponding mealVoteId)
        // Find the corresponding mealVote by ID
        Optional<MealVote> mealVoteOptional = foodRepository.findById(mealVoteId);
        // Check if the mealVote exists -> then increment the vote count
        if (mealVoteOptional.isPresent()) {
            MealVote mealVote = mealVoteOptional.get(); // get the MealVote object

            // Find all MealVotes for the same day and time
            List<MealVote> mealVotesForSameDayAndTime = foodRepository.findByMealDateAndMealTimeType(mealVote.getMealDate(), mealVote.getMealTimeType());

            // Remove the user's previous vote for the same day and time (only one vote is allowed per user)
            for (MealVote mv : mealVotesForSameDayAndTime) {
                String previousUserVoteKey = userId + "-" + mv.getId(); // form a unique key for the previous vote
                // check if the user has voted for this meal -> then decrement the vote count
                if (userVotes.contains(previousUserVoteKey)) {
                    mv.setVoteCount(mv.getVoteCount() - 1); // decrement the vote count
                    foodRepository.save(mv); // save the updated mealVote (with decremented vote count)
                    userVotes.remove(previousUserVoteKey); // remove the previous vote from the set
                    break; // exit the loop after decrementing the vote count for the first mealVote found
                }
            }

            // Increment the vote count for the new selection & save in DB:-
            mealVote.setVoteCount(mealVote.getVoteCount() + 1);  // increment the vote count
            foodRepository.save(mealVote); // save the updated mealVote (with incremented vote count)
            userVotes.add(userVoteKey); // add the new vote to the set
            return true; // Vote successfully cast
        } else {
            throw new IllegalArgumentException("Invalid mealVoteId.");
        }
    }

    /**
     * Retrieves the vote count for a specific meal.
     *
     * @param mealVoteId The ID of the meal whose vote count is to be retrieved.
     * @return The vote count for the specified meal.
     * @throws IllegalArgumentException If the mealVoteId is invalid.
     */
    // GET method to fetch the count of votes for a specific meal
    public int getVoteCount(Long mealVoteId) {
        // Find the mealVote by ID from the repository
        Optional<MealVote> mealVoteOptional = foodRepository.findById(mealVoteId);

        // Check if the mealVote exists -> then return the vote count
        if (mealVoteOptional.isPresent()) {
            return mealVoteOptional.get().getVoteCount();
        } else {
            throw new IllegalArgumentException("Invalid mealVoteId.");
        }
    }

}
