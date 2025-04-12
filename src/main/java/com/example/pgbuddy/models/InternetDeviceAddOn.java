package com.example.pgbuddy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "internet_device_addon")
public class InternetDeviceAddOn extends BaseModel {
    private Long devices;
    private Long price;
    private int validity;
    private boolean isRecommended;
}