package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.NoticeDto;
import com.example.pgbuddy.utils.JwtUtil;
import com.example.pgbuddy.services.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
//@CrossOrigin(origins = "http://localhost:5173") // Allow requests from React
public class NoticeController {
    private final NoticeService noticeService;
    private final JwtUtil jwtUtil;

    public NoticeController(NoticeService noticeService, JwtUtil jwtUtil) {
        this.noticeService = noticeService;
        this.jwtUtil = jwtUtil;
    }

    // This method is responsible for handling HTTP GET requests to retrieve all notices
    // @GetMapping -> maps HTTP GET requests to the method.
    @GetMapping
    // ResponseEntity is a Spring class that represents an HTTP response, including status code, headers, and body.
    public ResponseEntity<List<NoticeDto>> getAllNotices() {
        // used to fetch all notices (using Service Layer)
        List<NoticeDto> notices = noticeService.findAllNotices();
        // ResponseEntity.ok() creates a response with HTTP status 200 OK and the list of notices as the body.
        return ResponseEntity.ok(notices);
    }

    // a POSTMapping method to update the bookmarked status of a notice
    @PostMapping("/{id}/bookmark")
    public ResponseEntity<Void> updateBookmarkStatus(@PathVariable Long id, @RequestBody boolean bookmarked) {
        // Call the service to update the bookmark status
        noticeService.updateBookmarkStatus(id, bookmarked);
        // Return a response indicating success (HTTP 200 OK)
        return ResponseEntity.ok().build();
    }

    // POST method to create a new notice & store in DB
    @PostMapping("/publish")
    public ResponseEntity<NoticeDto> createNotice(@RequestHeader("Authorization") String token, @RequestBody NoticeRequestDto noticeRequestDto) {
        // Extract userId from the token
        Long userId = jwtUtil.extractUserId(token.substring(7)); // Remove "Bearer " prefix

        // Call the service to create a new notice
        NoticeDto createdNotice = noticeService.createNotice(noticeRequestDto, userId);
        // Return the created notice with HTTP status 201 Created
        return ResponseEntity.status(201).body(createdNotice);
    }


}

/*
ResponseEntity example for getAllNotices():
<200 OK OK, [NoticeDto(author=User(name=John Doe), title=Maintenance Notice, bookmarked=false, createdAtDay=2023-10-01, createdAtTime=10:00:00), NoticeDto(author=User(name=Jane Smith), title=Update Notice, bookmarked=true, createdAtDay=2023-10-05, createdAtTime=12:00:00)],[]>

The HTTP status is 200 OK.
The body contains a list of NoticeDto objects.
The headers are empty (represented by []).
 */
