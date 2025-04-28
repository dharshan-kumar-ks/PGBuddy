package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.FeedbackDto;
import com.example.pgbuddy.services.FeedbackService;
import com.example.pgbuddy.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling feedback-related requests from the account page.
 * Provides an endpoint to submit feedback data.
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final UserService userService;

    /**
     * Constructor for FeedbackController.
     *
     * @param feedbackService The service for handling feedback-related business logic.
     * @param userService The service for handling user-related operations.
     */
    public FeedbackController(FeedbackService feedbackService, UserService userService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    /**
     * Submits feedback data provided by the client.
     * Saves the feedback to the database.
     *
     * @param feedbackDto The request body containing feedback details.
     * @return A ResponseEntity containing the saved FeedbackDto and an HTTP status code.
     */
    // POST method to submit feedback
    // This method will accept feedback data from the client and save it to the database
    // TODO: keep the feedback and rating submission independent of each other
    @PostMapping
    public ResponseEntity<FeedbackDto> submitFeedback(@RequestBody FeedbackDto feedbackDto) {
        return new ResponseEntity<>(feedbackService.submitFeedback(feedbackDto), HttpStatus.CREATED);
    }
}
