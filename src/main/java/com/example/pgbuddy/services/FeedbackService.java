package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.FeedbackDto;
import com.example.pgbuddy.models.Feedback;
import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;
    private final UserService userService;

    public FeedbackService(FeedbackRepository feedbackRepository, UserService userService) {
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
    }

    public FeedbackDto submitFeedback(FeedbackDto feedbackDto) {
        // Validate the input
        if (feedbackDto.getComment() == null || feedbackDto.getComment().isEmpty()) {
            throw new IllegalArgumentException("Invalid feedback comment");
        }

        // Map FeedbackDto to Feedback entity
        Feedback feedback = new Feedback();
        feedback.setComment(feedbackDto.getComment());
        feedback.setRating(feedbackDto.getRating());

        // Fetch the User entity using the userId from FeedbackDto
        Long userId = Long.parseLong(feedbackDto.getUserId());
        User user = userService.findOnlyUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Set the User entity in the Feedback object
        feedback.setUser(user);

        // Save the Feedback entity to the database
        feedbackRepository.save(feedback);
        // get the feedback id & return the dto back
        feedbackDto.setId(feedback.getId());
        return feedbackDto;
    }


}
