package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.RoomCleaningResponseDto;
import com.example.pgbuddy.models.RoomCleaning;
import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.RoomCleaningRepository;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomCleaningService {
    private final RoomCleaningRepository roomCleaningRepository;
    private final UserRepository userRepository;

    public RoomCleaningService(RoomCleaningRepository roomCleaningRepository, UserRepository userRepository) {
        this.roomCleaningRepository = roomCleaningRepository;
        this.userRepository = userRepository;
    }

    // GET method to fetch room cleaning data (for the specific user)
    public RoomCleaningResponseDto getRoomCleaningUsage(Long userId) {
        // Get the latest RoomCleaning object from the repository for the userId
        RoomCleaning roomCleaning = roomCleaningRepository.findLatestByUserId(userId);


        RoomCleaningResponseDto roomCleaningResponse = new RoomCleaningResponseDto();
        roomCleaningResponse.setUserId(userId);
        // Set the cleaning date (based on latest RoomCleaning object) and cost
        if (roomCleaning != null && roomCleaning.getCleaningDate() != null) { roomCleaningResponse.setCleaningDate(roomCleaning.getCleaningDate().toString()); }
        else { roomCleaningResponse.setCleaningDate("N/A"); }
        roomCleaningResponse.setCost(100.0); // Always price is 100 for room cleaning

        return roomCleaningResponse;

    }

    public void requestRoomCleaning(Long userId) {
        // Get the User from the userId
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new RoomCleaning object
        RoomCleaning roomCleaning = new RoomCleaning();
        roomCleaning.setUser(user);
        roomCleaning.setCleaningDate(java.time.LocalDate.now()); // Set the cleaning date to today
        roomCleaning.setCost(100.0); // Set the cost to 100

        // Save the RoomCleaning object to the repository
        roomCleaningRepository.save(roomCleaning);
    }
}
