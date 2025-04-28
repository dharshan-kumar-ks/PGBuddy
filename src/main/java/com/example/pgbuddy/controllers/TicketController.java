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

/**
 * Controller for handling ticket-related requests.
 * Provides endpoints to create, retrieve, and update tickets.
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    /**
     * Constructor for TicketController.
     *
     * @param ticketService The service for handling ticket-related business logic.
     */
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Creates a new ticket entry in the database.
     *
     * @param ticketDto The request body containing the details of the ticket to create.
     * @return A ResponseEntity containing the created TicketDto and an HTTP status of 201 Created.
     */
    // POST Method to create a new ticket entry in DB table
    // This method will accept the ticket details from the client and save it in the database
    @PostMapping("/create")
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketDto) {
        TicketDto savedTicket = ticketService.createTicket(ticketDto); // Call the service to create a new ticket
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    /**
     * Retrieves a ticket by its ID.
     *
     * @param id The ID of the ticket to retrieve.
     * @return A ResponseEntity containing the TicketDto with the ticket details.
     */
    // GET Method to retrieve a ticket by its ticketID
    // This method will accept the ticket ID from the client and return the ticket details
    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable Long id) {
        TicketDto ticket = ticketService.getTicketById(id); // Call the service to get the ticket by ID
        return ResponseEntity.ok(ticket);
    }

    /**
     * Retrieves all tickets for a specific user by their user ID.
     *
     * @param userId The ID of the user whose tickets are to be retrieved.
     * @return A ResponseEntity containing a list of TicketDto objects for the user.
     */
    // GET Method to retrieve all ticket for a specific userID
    // This method will accept the user ID from the client and return the list of tickets for that user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDto>> getTicketsByUserId(@PathVariable Long userId) {
        List<TicketDto> tickets = ticketService.getTicketsByUserId(userId);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Retrieves all tickets in the system.
     *
     * @return A ResponseEntity containing a list of all TicketDto objects.
     */
    // GET Method to retrieve all tickets (for all users)
    // This method will return the list of all tickets in the system
    @GetMapping("/all")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        List<TicketDto> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    /**
     * Updates the resolution status of a ticket to "RESOLVED".
     *
     * @param ticketId The ID of the ticket to resolve.
     * @return A ResponseEntity containing the updated TicketDto with the resolved status.
     */
    // POST method to update the ticket ResolutionStatus to RESOLVED
    // This method will accept the ticket ID from the client and update the ticket status to resolved
    @PostMapping("/resolve/{ticketId}")
    public ResponseEntity<TicketDto> resolveTicket(@PathVariable Long ticketId) {
        TicketDto resolvedTicket = ticketService.resolveTicket(ticketId);
        return ResponseEntity.ok(resolvedTicket);
    }

}
