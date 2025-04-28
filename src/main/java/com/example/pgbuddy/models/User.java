package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// Model class for User details
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

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private String bloodGroup;
    private String address;
    private String companyName;
    private Date dateOfBirth; // Date of Birth

    private String profilePicture; // URL or path to the profile picture

    // Multiple Users can have same Notice (M:M)
    @ManyToMany
    @JoinTable(name = "user_notice_bookmarks", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "notice_id"))
    private Set<Notice> bookmarkedNotices = new HashSet<>();
}
