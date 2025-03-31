package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.UserDto;
import com.example.pgbuddy.models.User;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Find user by ID
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();
                //.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
        return mapToDTO(user);
    }

    // Helper method to map User entity to UserDto
    private UserDto mapToDTO(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(String.valueOf(user.getPhoneNumber()));
        dto.setUserType(user.getUserType().name());
        dto.setRoomId(user.getRoom() != null ? user.getRoom().getId() : null);
        return dto;
    }

}
