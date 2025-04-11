package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.CafeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeOrderRepository extends JpaRepository<CafeOrder, Long> {
    List<CafeOrder> findByUserId(Long userId);

    // Custom query methods can be defined here if needed
    // For example, to find orders by user ID or status
    // List<CafeOrder> findByUserId(Long userId);
    // List<CafeOrder> findByOrderStatus(String orderStatus);
}
