package com.example.pgbuddy.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private boolean success;
    private Long userId;
    private String token;
}
