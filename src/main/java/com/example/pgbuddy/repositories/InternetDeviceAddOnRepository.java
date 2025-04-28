package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.InternetDeviceAddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository interface for InternetDeviceAddOn entity
@Repository
public interface InternetDeviceAddOnRepository extends JpaRepository<InternetDeviceAddOn, Integer> {

}
