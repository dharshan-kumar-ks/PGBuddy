package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.CafeMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository interface for CafeMenu entity
@Repository
public interface CafeRepository extends JpaRepository<CafeMenu, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find menu items by availability or category
    List<CafeMenu> findByIsAvailableTrue();
    // List<Cafe_Menu> findByCategory(String category);
}
