package com.example.pgbuddy.Dtos;

import com.example.pgbuddy.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class ElectricityUsageDto {
    private double dailyUsageKwh;
    private double monthlyUsageKwh;
    private double estimatedCost;
    private Long userId;
}
