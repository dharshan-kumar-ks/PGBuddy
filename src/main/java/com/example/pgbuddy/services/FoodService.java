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

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final Set<String> userVotes = ConcurrentHashMap.newKeySet(); // stores (userId, mealVoteId) pair to track votes

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    // GET method to fetch food items based on MealDate & MealTime
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
        if (mealVoteOptional.isPresent()) {
            MealVote mealVote = mealVoteOptional.get();

            // Find all MealVotes for the same day and time
            List<MealVote> mealVotesForSameDayAndTime = foodRepository.findByMealDateAndMealTimeType(mealVote.getMealDate(), mealVote.getMealTimeType());

            // Remove the user's previous vote for the same day and time
            for (MealVote mv : mealVotesForSameDayAndTime) {
                String previousUserVoteKey = userId + "-" + mv.getId();
                if (userVotes.contains(previousUserVoteKey)) {
                    mv.setVoteCount(mv.getVoteCount() - 1);
                    foodRepository.save(mv);
                    userVotes.remove(previousUserVoteKey);
                    break;
                }
            }

            // Increment the vote count for the new selection
            mealVote.setVoteCount(mealVote.getVoteCount() + 1);
            foodRepository.save(mealVote);
            userVotes.add(userVoteKey);
            return true;
        } else {
            throw new IllegalArgumentException("Invalid mealVoteId.");
        }
    }

    // GET method to fetch the count of votes for a specific meal
    public int getVoteCount(Long mealVoteId) {
        Optional<MealVote> mealVoteOptional = foodRepository.findById(mealVoteId);
        if (mealVoteOptional.isPresent()) {
            return mealVoteOptional.get().getVoteCount();
        } else {
            throw new IllegalArgumentException("Invalid mealVoteId.");
        }
    }

}
