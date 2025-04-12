package com.example.pgbuddy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "electricity_usage")
public class ElectricityUsage extends BaseModel {
    private double dailyUsageKwh;
    private double monthlyUsageKwh;
    private double estimatedCost;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
