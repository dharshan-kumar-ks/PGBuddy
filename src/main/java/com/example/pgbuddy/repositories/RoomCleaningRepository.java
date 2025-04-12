package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.RoomCleaning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomCleaningRepository extends JpaRepository<RoomCleaning, Long> {
    // Custom query methods can be defined here if needed

    // find the lastest RoomCleaning by userId:  [Use Native SQL instead of JPQL -> LIMIT keyword is not supported in JPQL]
    @Query(value = "SELECT * FROM room_cleaning_sessions r WHERE r.user_id = :userId ORDER BY r.id DESC LIMIT 1", nativeQuery = true)
    RoomCleaning findLatestByUserId(@Param("userId") Long userId);
}
