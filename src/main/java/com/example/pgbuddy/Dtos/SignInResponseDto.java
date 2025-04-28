package com.example.pgbuddy.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO class for SignIn response
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private boolean success;
    private Long userId;
    private String userRole;
    private String token;
}
