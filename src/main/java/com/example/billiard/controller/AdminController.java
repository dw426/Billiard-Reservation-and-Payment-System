package com.example.billiard.controller;

import com.example.billiard.model.Reservation;
import com.example.billiard.repository.ReservationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin != null && isAdmin) {
            // ✅ 예약 목록을 모델에 담아서 View로 전달
            List<Reservation> reservations = reservationRepository.findAll();
            model.addAttribute("reservations", reservations);
            return "adminDashboard"; // templates/adminDashboard.html
        }
        return "redirect:/login";
    }
}
