package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.UserDto;
import com.example.pgbuddy.models.GenderType;
import com.example.pgbuddy.models.User;
import com.example.pgbuddy.models.UserType;
import com.example.pgbuddy.repositories.UserRepository;
import org.springframework.stereotype.Service;


/**
 * Service class for handling user-related operations, including retrieving user details,
 * updating user information, and mapping user entities to DTOs.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Constructor for UserService.
     *
     * @param userRepository Repository for accessing user data.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds a user by their ID and returns the user details as a UserDto.
     *
     * @param id The ID of the user to retrieve.
     * @return A UserDto object containing the user's details.
     * @throws RuntimeException If the user is not found.
     */
    // GET method to gind user by ID -> returns userDto
    public UserDto findUserById(Long id) {
        // Get the User object from the repository for the userId
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
                //.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
        return mapToDTO(user); // Convert the User object to UserDto & return it
    }

    /**
     * Helper method to map a User entity to a UserDto object.
     *
     * @param user The User entity to map.
     * @return A UserDto object containing the mapped user details.
     */
    // Helper method to map User entity to UserDto
    private UserDto mapToDTO(User user) {
        // Create a new UserDto object and set its properties
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
            .build(); // Build the UserDto object
    }

    /**
     * Finds a user by their ID and returns the User entity (not a DTO).
     *
     * @param id The ID of the user to retrieve.
     * @return The User entity.
     * @throws RuntimeException If the user is not found.
     */
    // Find user by ID -> returns user (not userDto)
    public User findOnlyUserById(Long id) {
        // Get the User object from the repository for the userId
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    /**
     * Updates the details of an existing user based on the provided UserDto.
     *
     * @param userDto The UserDto containing the updated user details.
     * @return A UserDto object representing the updated user.
     * @throws RuntimeException If the user is not found or if the date format is invalid.
     */
    // POST to fill in the missing user details (other than email and password)
    public UserDto fillUserDetails(UserDto userDto) {
        // Get the User object from the repository for the userId
        User user = userRepository.findById(userDto.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Update the User object & set its properties (based on the provided UserDto)
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

        userRepository.save(user); // Save the updated User object to the repository
        return mapToDTO(user); // Convert the updated User object to UserDto & return it
    }
}
