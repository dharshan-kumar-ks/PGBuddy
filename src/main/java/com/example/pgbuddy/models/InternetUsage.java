package com.example.pgbuddy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "internet_usage")
public class InternetUsage extends BaseModel {
    private double totalDataGb; // e.g., 105 GB
    private double dataLeftGb;  // e.g., 57.01 GB
    private int speedMbps;
    private int maxDevices;
    private LocalDate resetDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
