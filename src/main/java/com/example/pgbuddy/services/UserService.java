package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.UserDto;
import com.example.pgbuddy.models.GenderType;
import com.example.pgbuddy.models.User;
import com.example.pgbuddy.models.UserType;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Find user by ID -> returns userDto
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

    // Find user by ID -> returns user (not userDto)
    public User findOnlyUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    // POST to fill in the missing user details (other than email and password)
    public UserDto fillUserDetails(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getPhoneNumber() != null) user.setPhoneNumber(userDto.getPhoneNumber());
        if (userDto.getGender() != null) user.setGender(GenderType.valueOf(userDto.getGender()));
        if (userDto.getBloodGroup() != null) user.setBloodGroup(userDto.getBloodGroup());
        if (userDto.getAddress() != null) user.setAddress(userDto.getAddress());
        if (userDto.getCompanyName() != null) user.setCompanyName(userDto.getCompanyName());
        if (userDto.getDateOfBirth() != null) {
            try {
                user.setDateOfBirth(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(userDto.getDateOfBirth()));
            } catch (java.text.ParseException e) {
                throw new RuntimeException("Invalid date format. Expected yyyy-MM-dd");
            }
        }
        if (userDto.getProfilePicture() != null) user.setProfilePicture(userDto.getProfilePicture());
        if (userDto.getUserType() != null) user.setUserType(UserType.valueOf(userDto.getUserType()));

        userRepository.save(user);
        return mapToDTO(user);
    }
}
