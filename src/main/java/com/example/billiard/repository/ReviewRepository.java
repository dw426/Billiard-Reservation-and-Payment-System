package com.example.billiard.repository;

import com.example.billiard.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserEmail(String userEmail);
}
