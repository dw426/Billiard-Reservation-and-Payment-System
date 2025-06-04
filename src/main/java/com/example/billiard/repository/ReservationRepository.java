package com.example.billiard.repository;

import com.example.billiard.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // ✅ 이메일 기준으로 사용자 예약 목록 조회
    List<Reservation> findByUserEmail(String userEmail);
}
