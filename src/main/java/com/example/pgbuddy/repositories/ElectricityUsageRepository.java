package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.ElectricityUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repository interface for ElectricityUsage entity
@Repository
public interface ElectricityUsageRepository extends JpaRepository<ElectricityUsage, Long> {
    // Custom query methods can be defined here if needed

    // find ElectricityUsage by userId:
    Optional<ElectricityUsage> findByUserId(Long userId);
}
