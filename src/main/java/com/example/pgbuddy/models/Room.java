package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "rooms")
public class Room extends BaseModel {
    private int number;

    @Enumerated(EnumType.STRING)
    private RoomType roomType; // 4 types of rooms available

    private int currentCapacity;
    private boolean available; // if room is not fully occupied

    // The users table contains the foreign key (room_id), not the rooms table.
    // The rooms table doesn’t need an extra column to track users—Hibernate uses the room_id column in users to figure out which users belong to a room.
    //@OneToMany(mappedBy = "roomId")
    //private List<User> residents = new ArrayList<>();
    // Useful for fetching all residents of a room (e.g., for the "Roommate Finder" feature in project).
}
