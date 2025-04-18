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

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository; // Need this to fetch users

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // Logic to get the ticket by ID
    public TicketDto getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        return mapToDto(ticket);
    }

    // Logic to save the ticketDto to the database
    public TicketDto createTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDto.getTitle());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setPriority(PriorityType.valueOf(ticketDto.getPriority()));
        ticket.setTicketType(TicketType.valueOf(ticketDto.getTicketType()));
        ticket.setStatus(ResolutionStatus.valueOf(ticketDto.getStatus()));
        ticket.setCreatedAt(LocalDateTime.now());

        // Set user and assignedTo manually if they exist
        if (ticketDto.getUserId() != null) {
            User user = userRepository.findById(ticketDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            ticket.setUser(user);
        }
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

    // convert Ticket entity to TicketDto entity
    private TicketDto mapToDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setPriority(ticket.getPriority().name());
        dto.setTicketType(ticket.getTicketType().name());
        dto.setStatus(ticket.getStatus().name());
        dto.setCreatedAt(ticket.getCreatedAt());

        if (ticket.getUser() != null) {
            dto.setUserId(ticket.getUser().getId());
        }
        if (ticket.getAssignedTo() != null) {
            dto.setAssignedTo(ticket.getAssignedTo().getId());
        }

        return dto;
    }

    // GET Method to retrieve all ticket for a specific userID
    public List<TicketDto> getTicketsByUserId(Long userId) {
        List<Ticket> tickets = ticketRepository.findByUserId(userId);
        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("No tickets found for this user");
        }
        return tickets.stream().map(this::mapToDto).toList();
    }

    // GET Method to retrieve all tickets (for all users)
    public List<TicketDto> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("No tickets found");
        }
        return tickets.stream().map(this::mapToDto).toList();
    }
}
