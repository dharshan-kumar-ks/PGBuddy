package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.FeedbackDto;
import com.example.pgbuddy.models.Feedback;
import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.FeedbackRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for handling feedback-related operations.
 */
@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;
    private final UserService userService;

    /**
     * Constructor for FeedbackService.
     *
     * @param feedbackRepository Repository for accessing feedback data.
     * @param userService Service for accessing user-related operations.
     */
    public FeedbackService(FeedbackRepository feedbackRepository, UserService userService) {
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
    }

    /**
     * Submits feedback provided by a user.
     *
     * @param feedbackDto The feedback details provided by the user.
     * @return A FeedbackDto containing the saved feedback details, including the generated feedback ID.
     * @throws IllegalArgumentException If the feedback comment is null or empty.
     * @throws RuntimeException If the user associated with the feedback is not found.
     */
    // POST method to submit feedback
    public FeedbackDto submitFeedback(FeedbackDto feedbackDto) {
        // Validate the input - to see if the feedback comment is not null or empty -> else throw an exception
        if (feedbackDto.getComment() == null || feedbackDto.getComment().isEmpty()) {
            throw new IllegalArgumentException("Invalid feedback comment");
        }

        // Map FeedbackDto to Feedback entity:-
        // Create a new Feedback object and set its properties
        Feedback feedback = new Feedback();
        feedback.setComment(feedbackDto.getComment());
        feedback.setRating(feedbackDto.getRating());

        // Fetch the User entity using the userId from FeedbackDto
        Long userId = Long.parseLong(feedbackDto.getUserId());
        User user = userService.findOnlyUserById(userId);
        if (user == null) { // Check if the user exists -> else throw an exception
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
