package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.TicketDto;
import com.example.pgbuddy.exceptions.InvalidEnumValueException;
import com.example.pgbuddy.exceptions.ResourceNotFoundException;
import com.example.pgbuddy.repositories.TicketRepository;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;
import com.example.pgbuddy.models.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for handling ticket-related operations, including creating, retrieving, and resolving tickets.
 */
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository; // Need this to fetch users

    /**
     * Constructor for TicketService.
     *
     * @param ticketRepository Repository for accessing ticket data.
     * @param userRepository Repository for accessing user data.
     */
    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a ticket by its ID.
     *
     * @param id The ID of the ticket to retrieve.
     * @return A TicketDto object representing the ticket.
     * @throws ResourceNotFoundException If the ticket is not found.
     */
    // GET Method to retrieve a ticket by its ticketID
    public TicketDto getTicketById(Long id) {
        // Get the Ticket object from the repository for the ticketId
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        return mapToDto(ticket); // Convert the Ticket object to TicketDto & return it
    }

    /**
     * Creates a new ticket and saves it to the database.
     *
     * @param ticketDto The details of the ticket to create.
     * @return A TicketDto object representing the created ticket.
     * @throws ResourceNotFoundException If the user or assigned user is not found.
     */
    // POST Method to create a new ticket entry in DB table
    public TicketDto createTicket(TicketDto ticketDto) {
        // Create a new Ticket object & set its properties
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDto.getTitle());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setPriority(PriorityType.valueOf(ticketDto.getPriority()));
        ticket.setTicketType(TicketType.valueOf(ticketDto.getTicketType()));
        ticket.setCategory(CategoryType.valueOf(ticketDto.getCategory()));
        ticket.setStatus(ResolutionStatus.valueOf(ticketDto.getStatus()));
        ticket.setCreatedAt(LocalDateTime.now());

        // Set user and assignedTo manually if they exist :-
        // Check if userId is not null and fetch the userId
        if (ticketDto.getUserId() != null) {
            User user = userRepository.findById(ticketDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            ticket.setUser(user);
        }
        // Check if assignedTo is not null and fetch the userId
        if (ticketDto.getAssignedTo() != null) {
            User assignedUser = userRepository.findById(ticketDto.getAssignedTo())
                    .orElseThrow(() -> new ResourceNotFoundException("Assigned user not found"));
            ticket.setAssignedTo(assignedUser);
        }

        // Save the ticket entity to the database
        Ticket savedTicket = ticketRepository.save(ticket);
        // Convert the saved Ticket entity back to TicketDto
        return mapToDto(savedTicket);
    }

    /**
     * Converts a Ticket entity to a TicketDto object.
     *
     * @param ticket The Ticket entity to convert.
     * @return A TicketDto object representing the ticket.
     */
    // convert Ticket entity to TicketDto entity
    private TicketDto mapToDto(Ticket ticket) {
        // Create a new TicketDto object and set its properties
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setPriority(ticket.getPriority().name());
        // first letter capitalized and the rest in lowercase
        dto.setCategory(ticket.getCategory().name().substring(0, 1).toUpperCase() + ticket.getCategory().name().substring(1).toLowerCase());
        dto.setTicketType(ticket.getTicketType().name());
        dto.setStatus(ticket.getStatus().name());
        dto.setCreatedAt(ticket.getCreatedAt());

        // Set userId and assignedTo if they exist :-
        // Check if userId is not null & fetch the userId
        if (ticket.getUser() != null) {
            dto.setUserId(ticket.getUser().getId());
        }
        // Check if assignedTo is not null & fetch the userId
        if (ticket.getAssignedTo() != null) {
            dto.setAssignedTo(ticket.getAssignedTo().getId());
        }

        return dto;
    }

    /**
     * Retrieves all tickets for a specific user by their user ID.
     *
     * @param userId The ID of the user whose tickets are to be retrieved.
     * @return A list of TicketDto objects representing the user's tickets.
     * @throws ResourceNotFoundException If no tickets are found for the user.
     */
    // GET Method to retrieve all ticket for a specific userID
    public List<TicketDto> getTicketsByUserId(Long userId) {
        List<Ticket> tickets = ticketRepository.findByUserId(userId);
        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("No tickets found for this user");
        }
        return tickets.stream().map(this::mapToDto).toList();
    }

    /**
     * Retrieves all tickets for all users.
     *
     * @return A list of TicketDto objects representing all tickets.
     * @throws ResourceNotFoundException If no tickets are found.
     */
    // GET Method to retrieve all tickets (for all users)
    public List<TicketDto> getAllTickets() {
        // Fetch all tickets from the repository
        List<Ticket> tickets = ticketRepository.findAll();
        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("No tickets found");
        }
        // Convert the list of Ticket objects to a list of TicketDto objects
        return tickets.stream().map(this::mapToDto).toList();
    }

    /**
     * Updates the resolution status of a ticket to RESOLVED.
     *
     * @param ticketId The ID of the ticket to resolve.
     * @return A TicketDto object representing the resolved ticket.
     * @throws ResourceNotFoundException If the ticket is not found.
     */
    // POST method to update the ticket ResolutionStatus to RESOLVED
    public TicketDto resolveTicket(Long ticketId) {
        // Get the Ticket object from the repository for the ticketId
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        ticket.setStatus(ResolutionStatus.RESOLVED); // Set the status to RESOLVED
        Ticket resolvedTicket = ticketRepository.save(ticket); // Save the updated ticket entity in the database
        return mapToDto(resolvedTicket); // Convert the Ticket object to TicketDto & return it
    }
}
