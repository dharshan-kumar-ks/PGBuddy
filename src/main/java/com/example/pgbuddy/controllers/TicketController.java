package com.example.pgbuddy.controllers;

import com.example.pgbuddy.Dtos.TicketDto;
import com.example.pgbuddy.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // POST Method to create a new ticket entry in DB table
    @PostMapping("/create")
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketDto) {
        TicketDto savedTicket = ticketService.createTicket(ticketDto);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    // GET Method to retrieve a ticket by its ticketID
    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable Long id) {
        TicketDto ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    // GET Method to retrieve all ticket for a specific userID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDto>> getTicketsByUserId(@PathVariable Long userId) {
        List<TicketDto> tickets = ticketService.getTicketsByUserId(userId);
        return ResponseEntity.ok(tickets);
    }

    // GET Method to retrieve all tickets (for all users)
    @GetMapping("/all")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        List<TicketDto> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    // POST method to update the ticket ResolutionStatus to RESOLVED
    @PostMapping("/resolve/{ticketId}")
    public ResponseEntity<TicketDto> resolveTicket(@PathVariable Long ticketId) {
        TicketDto resolvedTicket = ticketService.resolveTicket(ticketId);
        return ResponseEntity.ok(resolvedTicket);
    }

}
