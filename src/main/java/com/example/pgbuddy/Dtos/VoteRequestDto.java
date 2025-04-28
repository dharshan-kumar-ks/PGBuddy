package com.example.pgbuddy.Dtos;

import lombok.Getter;
import lombok.Setter;

// DTO class for Vote request
@Getter
@Setter
public class VoteRequestDto {
    private Long mealVoteId;
    private Long userId;
}
