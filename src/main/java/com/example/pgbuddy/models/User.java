package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseModel {
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false) // Ensure password is not null
    private String password; // encrypted password

    @Column(unique = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserType userType; // RESIDENT/ADMIN

    // Multiple Users can have same Room
    @ManyToOne
    // the column in the users table will be called room_id -> represents foreign key
    // Without @JoinColumn, JPA would generate a default column name (e.g., room_id)
    @JoinColumn(name = "room_id")
    private Room room;

}
