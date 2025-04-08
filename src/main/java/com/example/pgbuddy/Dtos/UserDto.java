package com.example.pgbuddy.Dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String userType; // RESIDENT or ADMIN
    private Long roomId;     // Basic room info for Roommate Finder
    private String gender;    // MALE or FEMALE
    private String bloodGroup;
    private String address;
    private String companyName;
    private String dateOfBirth; // Date of Birth in String format
    private String profilePicture; // URL or path to the profile picture
}