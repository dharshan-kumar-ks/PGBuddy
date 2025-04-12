package com.example.pgbuddy.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomCleaningResponseDto {
    private String cleaningDate;
    private double cost;
    private Long userId;
}
