package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Get the user in DB (based on the email)
    Optional<User> findByEmail(String email);

    // Get the user in DB (based on the id)
    //Optional<User> findById(Long Id);
}
