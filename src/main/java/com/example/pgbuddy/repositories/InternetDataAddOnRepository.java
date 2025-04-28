package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.InternetDataAddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository interface for InternetDataAddOn entity
@Repository
public interface InternetDataAddOnRepository extends JpaRepository<InternetDataAddOn, Integer> {

}
