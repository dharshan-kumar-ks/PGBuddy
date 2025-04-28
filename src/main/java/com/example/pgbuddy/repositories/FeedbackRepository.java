package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository interface for Feedback entity
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
