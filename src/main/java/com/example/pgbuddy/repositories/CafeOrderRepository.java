package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.CafeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeOrderRepository extends JpaRepository<CafeOrder, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find orders by user ID or status
    // List<CafeOrder> findByUserId(Long userId);
    // List<CafeOrder> findByOrderStatus(String orderStatus);
}
