package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// Repository interface for Booking entity
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Custom query to find a booking by user ID
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
    Booking findByUserId(@Param("userId") Long userId);
}

