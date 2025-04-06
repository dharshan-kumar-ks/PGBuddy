package com.example.pgbuddy.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequestDto {
    private Long mealVoteId;
    private Long userId;
}
