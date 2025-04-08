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
        return UserDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName() != null ? user.getName() : "")
            .phoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : "")
            .gender(user.getGender() != null ? user.getGender().toString() : "")
            .bloodGroup(user.getBloodGroup() != null ? user.getBloodGroup() : "")
            .address(user.getAddress() != null ? user.getAddress() : "")
            .companyName(user.getCompanyName() != null ? user.getCompanyName() : "")
            .dateOfBirth(user.getDateOfBirth() != null
                ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(user.getDateOfBirth())
                : "")
            .profilePicture(user.getProfilePicture() != null ? user.getProfilePicture() : "")
            .userType(user.getUserType() != null ? user.getUserType().name() : "")
            .build();
    }

}
