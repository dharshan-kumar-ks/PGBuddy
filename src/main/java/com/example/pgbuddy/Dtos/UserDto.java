package com.example.pgbuddy.Dtos;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String userType; // RESIDENT or ADMIN
    private Long roomId;     // Basic room info for Roommate Finder
}