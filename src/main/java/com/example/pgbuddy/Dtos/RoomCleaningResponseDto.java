package com.example.pgbuddy.Dtos;

import lombok.Data;

import java.time.LocalDate;

// DTO class for Room Cleaning details
@Data
public class RoomCleaningResponseDto {
    private String cleaningDate;
    private double cost;
    private Long userId;
}
