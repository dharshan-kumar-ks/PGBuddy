package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.NoticeDto;
import com.example.pgbuddy.Dtos.UserDto;
import com.example.pgbuddy.services.NoticeService;
import com.example.pgbuddy.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@CrossOrigin(origins = "http://localhost:5173") // Allow requests from React
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
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

    /*
    // Get user by ID (for Roommate Finder or profile view)
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDto> getUserById(@PathVariable Long id) {
        NoticeDto notice = noticeService.findUserById(id);
        return ResponseEntity.ok(user);
    }
    */

}

/*
ResponseEntity example for getAllNotices():
<200 OK OK, [NoticeDto(author=User(name=John Doe), title=Maintenance Notice, bookmarked=false, createdAtDay=2023-10-01, createdAtTime=10:00:00), NoticeDto(author=User(name=Jane Smith), title=Update Notice, bookmarked=true, createdAtDay=2023-10-05, createdAtTime=12:00:00)],[]>

The HTTP status is 200 OK.
The body contains a list of NoticeDto objects.
The headers are empty (represented by []).
 */
