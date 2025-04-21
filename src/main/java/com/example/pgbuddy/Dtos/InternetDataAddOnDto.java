package com.example.pgbuddy.Dtos;

import lombok.Data;

@Data
public class InternetDataAddOnDto {
    private int packId;
    private Long data;
    private Long price;
    private int validity;
    private boolean isRecommended;
}
