package com.example.billiard.controller;

import com.example.billiard.model.Reservation;
import com.example.billiard.model.Review;
import com.example.billiard.model.User;
import com.example.billiard.repository.ReservationRepository;
import com.example.billiard.repository.ReviewRepository;
import com.example.billiard.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    // ✅ 관리자 대시보드
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) return "redirect:/login";

        List<Reservation> reservations = reservationRepository.findAll();
        model.addAttribute("reservations", reservations);
        return "adminDashboard";
    }

    // ✅ 전체 예약 확인
    @GetMapping("/admin/reservations")
    public String viewAllReservations(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) return "redirect:/login";

        List<Reservation> reservations = reservationRepository.findAll();
        model.addAttribute("reservations", reservations);
        return "adminReservationList"; // templates/adminReservationList.html
    }

    // ✅ 전체 계정 목록 확인
    @GetMapping("/admin/accounts")
    public String viewAllAccounts(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) return "redirect:/login";

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "adminAccountList"; // templates/adminAccountList.html
    }
}
