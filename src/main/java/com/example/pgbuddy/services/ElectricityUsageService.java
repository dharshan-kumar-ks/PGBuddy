package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.ElectricityUsageDto;
import com.example.pgbuddy.models.ElectricityUsage;
import com.example.pgbuddy.repositories.ElectricityUsageRepository;
import org.springframework.stereotype.Service;

@Service
public class ElectricityUsageService {
    private final ElectricityUsageRepository electricityUsageRepository;

    public ElectricityUsageService(ElectricityUsageRepository electricityUsageRepository) {
        this.electricityUsageRepository = electricityUsageRepository;
    }

    // GET method to fetch electricity usage data (for the specific user)
    public ElectricityUsageDto getElectricityUsage(Long userId) {
        // Get the ElectricityUsage object from the repository for the userId
        ElectricityUsage electricityUsage = electricityUsageRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Electricity usage not found for user ID: " + userId));

        // Convert the ElectricityUsage object to ElectricityUsageDto
        ElectricityUsageDto electricityUsageDto = new ElectricityUsageDto();
        electricityUsageDto.setDailyUsageKwh(electricityUsage.getDailyUsageKwh());
        electricityUsageDto.setMonthlyUsageKwh(electricityUsage.getMonthlyUsageKwh());
        electricityUsageDto.setEstimatedCost(electricityUsage.getEstimatedCost());
        electricityUsageDto.setUserId(electricityUsage.getUser().getId());

        return electricityUsageDto;
    }
}
