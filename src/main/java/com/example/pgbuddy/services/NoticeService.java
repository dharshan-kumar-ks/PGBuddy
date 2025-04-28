package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.NoticeDto;
import com.example.pgbuddy.Dtos.NoticeRequestDto;
import com.example.pgbuddy.models.Notice;
import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.NoticeRepository;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service class for handling notice-related operations, including retrieving, bookmarking, and creating notices.
 */
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for NoticeService.
     *
     * @param noticeRepository Repository for accessing notice data.
     * @param userRepository Repository for accessing user data.
     */
    public NoticeService(NoticeRepository noticeRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all notices, sorted by creation date in descending order, and checks if a user has bookmarked them.
     *
     * @param userId The ID of the user to check for bookmarked notices.
     * @return A list of NoticeDto objects representing the notices.
     */
    // GET method to retrieve all notices
    public List<NoticeDto> findAllNotices(Long userId) {
        // Fetch all notices from the repository
        return noticeRepository.findAll()
                .stream() // Convert to stream for processing
                .sorted((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt())) // Sort by CreatedAt in descending order
                .map(notice -> { // Map each Notice --> to NoticeDto
                    // Create a new NoticeDto object and set its properties
                    NoticeDto dto = new NoticeDto();
                    dto.setId(notice.getId());
                    dto.setAuthorName(notice.getAuthor().getName());
                    dto.setTitle(notice.getTitle());

                    // Get the user by userId
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    // Check if the user has bookmarked this notice
                    boolean isBookmarked = user.getBookmarkedNotices().contains(notice);
                    dto.setBookmarked(isBookmarked);

                    // Format createdAt into day and time components
                    LocalDateTime createdAt = notice.getCreatedAt(); // Assuming this returns LocalDateTime
                    if (createdAt != null) {
                        dto.setCreatedAtDay(createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // Format date to "yyyy-MM-dd"
                        dto.setCreatedAtTime(createdAt.format(DateTimeFormatter.ofPattern("HH:mm:ss"))); // Format time to "HH:mm:ss"
                    }

                    return dto; // Return the populated NoticeDto
                })
                .toList(); // Collect the results into a List
    }

    /**
     * Updates the bookmarked status of a notice for a specific user.
     *
     * @param userId The ID of the user updating the bookmark status.
     * @param id The ID of the notice to update.
     * @param bookmarked The new bookmark status (true for bookmarked, false for unbookmarked).
     */
    // POST method to update the bookmarked status of a notice
    public void updateBookmarkStatus(Long userId, Long id, boolean bookmarked) {
        // Find the user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Find the notice by ID
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));

        // Check if the user has already bookmarked the notice
        if (bookmarked) {
            // If the user is bookmarking the notice, add it to their bookmarked notices
            user.getBookmarkedNotices().add(notice);
        } else {
            // If the user is unbookmarking the notice, remove it from their bookmarked notices
            user.getBookmarkedNotices().remove(notice);
        }

        // Update the user's bookmarked notices in the repository
        userRepository.save(user);

        // Update the bookmarked status
        //notice.setBookmarked(bookmarked);

        // Save the updated notice back to the repository
        //noticeRepository.save(notice);
    }

    /**
     * Creates a new notice and stores it in the database.
     *
     * @param noticeRequestDto The details of the notice to be created.
     * @param authorId The ID of the user creating the notice.
     * @return A NoticeDto object representing the created notice.
     */
    // POST method to create a new notice & store in DB
    public NoticeDto createNotice(NoticeRequestDto noticeRequestDto, Long authorId) {
        // Find the user by authorId
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Notice object & set its properties
        Notice notice = new Notice();
        notice.setTitle(noticeRequestDto.getTitle());
        notice.setAuthor(author);
        notice.setBookmarked(false);
        notice.setCreatedAt(LocalDateTime.now());
        notice.setLastModifiedAt(LocalDateTime.now());

        // Save the notice to the repository
        noticeRepository.save(notice);

        // Return the created notice as a DTO :-
        // Create a new NoticeDto object and set its properties
        NoticeDto createdNotice = new NoticeDto();
        createdNotice.setId(notice.getId());
        createdNotice.setAuthorName(author.getName());
        createdNotice.setTitle(notice.getTitle());
        createdNotice.setBookmarked(notice.isBookmarked());
        createdNotice.setCreatedAtDay(notice.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        createdNotice.setCreatedAtTime(notice.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return createdNotice; // return the created notice DTO
    }
}
