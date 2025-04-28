package com.example.pgbuddy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

// InternetDataAddOn class representing the internet data add-on details
@Data
@Entity
@Table(name = "internet_data_addon")
public class InternetDataAddOn extends BaseModel {
    private Long data;
    private Long price;
    private int validity;
    private boolean isRecommended;
}
