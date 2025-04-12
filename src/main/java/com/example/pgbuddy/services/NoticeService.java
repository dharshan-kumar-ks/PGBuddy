package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.NoticeDto;
import com.example.pgbuddy.controllers.NoticeRequestDto;
import com.example.pgbuddy.models.Notice;
import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.NoticeRepository;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public NoticeService(NoticeRepository noticeRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
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

    public void updateBookmarkStatus(Long id, boolean bookmarked) {
        // Find the notice by ID
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));

        // Update the bookmarked status
        notice.setBookmarked(bookmarked);

        // Save the updated notice back to the repository
        noticeRepository.save(notice);
    }

    // POST method to create a new notice & store in DB
    public NoticeDto createNotice(NoticeRequestDto noticeRequestDto, Long authorId) {
        // Find the user by authorId
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Notice object
        Notice notice = new Notice();
        notice.setTitle(noticeRequestDto.getTitle());
        notice.setAuthor(author);
        notice.setBookmarked(false);
        notice.setCreatedAt(LocalDateTime.now());
        notice.setLastModifiedAt(LocalDateTime.now());

        // Save the notice to the repository
        noticeRepository.save(notice);

        // Return the created notice as a DTO
        NoticeDto createdNotice = new NoticeDto();
        createdNotice.setId(notice.getId());
        createdNotice.setAuthorName(author.getName());
        createdNotice.setTitle(notice.getTitle());
        createdNotice.setBookmarked(notice.isBookmarked());
        createdNotice.setCreatedAtDay(notice.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        createdNotice.setCreatedAtTime(notice.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return createdNotice;
    }
}
