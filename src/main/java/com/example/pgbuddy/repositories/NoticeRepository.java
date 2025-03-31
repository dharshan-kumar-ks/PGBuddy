package com.example.pgbuddy.repositories;

import com.example.pgbuddy.models.Notice;
import com.example.pgbuddy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
