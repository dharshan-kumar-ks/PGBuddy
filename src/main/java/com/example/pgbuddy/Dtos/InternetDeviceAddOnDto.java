package com.example.pgbuddy.Dtos;

import com.example.pgbuddy.models.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
public class InternetDeviceAddOnDto {
    private int packId;
    private Long devices;
    private Long price;
    private int validity;
    private boolean isRecommended;
}