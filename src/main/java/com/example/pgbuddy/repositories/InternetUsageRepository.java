package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.InternetUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternetUsageRepository extends JpaRepository<InternetUsage, Long> {
    // Custom query methods can be defined here if needed

    // find InternetUsage by userId:
    Optional<InternetUsage> findByUserId(Long userId);
}
