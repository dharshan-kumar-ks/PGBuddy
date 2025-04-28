package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.RoomCleaningResponseDto;
import com.example.pgbuddy.models.RoomCleaning;
import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.RoomCleaningRepository;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for handling room cleaning-related operations, including fetching usage data
 * and requesting room cleaning services.
 */
@Service
public class RoomCleaningService {
    private final RoomCleaningRepository roomCleaningRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for RoomCleaningService.
     *
     * @param roomCleaningRepository Repository for accessing room cleaning data.
     * @param userRepository Repository for accessing user data.
     */
    public RoomCleaningService(RoomCleaningRepository roomCleaningRepository, UserRepository userRepository) {
        this.roomCleaningRepository = roomCleaningRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the latest room cleaning usage data for a specific user.
     *
     * @param userId The ID of the user whose room cleaning usage data is to be retrieved.
     * @return A RoomCleaningResponseDto containing the room cleaning details, including the cleaning date and cost.
     */
    // GET method to fetch room cleaning data (for the specific user)
    public RoomCleaningResponseDto getRoomCleaningUsage(Long userId) {
        // Get the latest RoomCleaning object from the repository for the userId
        RoomCleaning roomCleaning = roomCleaningRepository.findLatestByUserId(userId);

        // Convert the RoomCleaning object to RoomCleaningResponseDto :-
        // Create a new RoomCleaningResponseDto object & set its properties
        RoomCleaningResponseDto roomCleaningResponse = new RoomCleaningResponseDto();
        roomCleaningResponse.setUserId(userId);
        // Set the cleaning date (based on latest RoomCleaning object) and cost
        if (roomCleaning != null && roomCleaning.getCleaningDate() != null) { roomCleaningResponse.setCleaningDate(roomCleaning.getCleaningDate().toString()); }
        else { roomCleaningResponse.setCleaningDate("N/A"); }
        roomCleaningResponse.setCost(100.0); // Always price is 100 for room cleaning

        return roomCleaningResponse; // Return the RoomCleaningResponseDto object
    }

    /**
     * Requests a room cleaning service for a specific user.
     *
     * @param userId The ID of the user requesting the room cleaning service.
     * @throws RuntimeException If the user with the given ID is not found.
     */
    // POST method to request room cleaning service (save the room cleaning data in DB)
    public void requestRoomCleaning(Long userId) {
        // Get the User from the userId
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new RoomCleaning object & set its properties
        RoomCleaning roomCleaning = new RoomCleaning();
        roomCleaning.setUser(user);
        roomCleaning.setCleaningDate(java.time.LocalDate.now()); // Set the cleaning date to today
        roomCleaning.setCost(100.0); // Set the cost to 100

        // Save the RoomCleaning object to the repository
        roomCleaningRepository.save(roomCleaning);
    }
}
