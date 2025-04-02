package com.example.pgbuddy.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponseDto {
    private boolean success;
    private Long userId;

    public SignInResponseDto(boolean success, Long userId) {
        this.success = success;
        this.userId = userId;
    }
}
