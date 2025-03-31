package com.example.pgbuddy.Dtos;

import com.example.pgbuddy.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {
    private Long id;
    private String authorName;
    private String title;
    //private String content;
    private boolean bookmarked;
    private String createdAtDay;
    private String createdAtTime;
}
