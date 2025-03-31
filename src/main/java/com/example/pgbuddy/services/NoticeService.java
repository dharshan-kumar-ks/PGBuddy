package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.NoticeDto;
import com.example.pgbuddy.repositories.NoticeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<NoticeDto> findAllNotices() {
        return noticeRepository.findAll()
                .stream()
                .map(notice -> {
                    NoticeDto dto = new NoticeDto();
                    dto.setId(notice.getId());
                    dto.setAuthorName(notice.getAuthor().getName());
                    dto.setTitle(notice.getTitle());
                    dto.setBookmarked(notice.isBookmarked());

                    // Format createdAt into day and time components
                    LocalDateTime createdAt = notice.getCreatedAt(); // Assuming this returns LocalDateTime
                    if (createdAt != null) {
                        dto.setCreatedAtDay(createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        dto.setCreatedAtTime(createdAt.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                    }

                    return dto;
                })
                .toList();
    }
}
