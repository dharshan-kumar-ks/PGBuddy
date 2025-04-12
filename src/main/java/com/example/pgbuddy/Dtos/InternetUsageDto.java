package com.example.pgbuddy.Dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InternetUsageDto {
    private double totalDataGb; // e.g., 105 GB
    private double dataLeftGb;  // e.g., 57.01 GB
    private int speedMbps;
    private int maxDevices;
    private String resetDate;
    private Long userId; // Assuming you want to include the user ID in the DTO
}
