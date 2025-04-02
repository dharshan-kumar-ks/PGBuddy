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
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
                //.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
        return mapToDTO(user);
    }

    // Helper method to map User entity to UserDto
    private UserDto mapToDTO(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        if (user.getUserType() != null) {
            userDto.setUserType(user.getUserType().name());
        } else {
            userDto.setUserType(null); // or set a default value if needed
        }
        return userDto;
    }

}
