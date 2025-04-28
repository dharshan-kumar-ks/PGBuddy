package com.example.pgbuddy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

// RoomCleaning class representing the room cleaning sessions
@Data
@Entity
@Table(name = "room_cleaning_sessions")
public class RoomCleaning extends BaseModel {
    private LocalDate cleaningDate;
    private double cost;

    // Multiple RoomCleaning sessions can be requested by 1 User (M:1)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
