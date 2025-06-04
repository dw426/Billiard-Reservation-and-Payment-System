package com.example.billiard.controller;

import com.example.billiard.model.Reservation;
import com.example.billiard.model.User;
import com.example.billiard.repository.ReservationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    // 예약 폼 페이지
    @GetMapping("/reservation")
    public String showReservationForm(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("reservation", new Reservation());
        return "reservation";  // templates/reservation.html
    }

    // 예약 처리
    @PostMapping("/reservation")
    public String processReservation(@ModelAttribute Reservation reservation,
                                     HttpSession session,
                                     Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        reservation.setUserEmail(user.getEmail());
        reservationRepository.save(reservation);

        model.addAttribute("message", "예약이 성공적으로 완료되었습니다!");
        return "success";  // templates/success.html
    }

    // 성공 페이지
    @GetMapping("/success")
    public String successPage(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message != null ? message : "정상적으로 처리되었습니다.");
        return "success";
    }

    // 사용자 예약 목록 보기
    @GetMapping("/my-reservations")
    public String myReservations(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Reservation> reservations = reservationRepository.findByUserEmail(user.getEmail());
        model.addAttribute("reservations", reservations);
        return "myReservations"; // templates/myReservations.html
    }

    // 예약 취소 (사용자 본인만 삭제 가능)
    @PostMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation != null && reservation.getUserEmail().equals(user.getEmail())) {
            reservationRepository.deleteById(id);
        }

        return "redirect:/my-reservations";
    }

    // 관리자 예약 삭제
    @PostMapping("/reservation/delete/{id}")
    public String deleteReservationAsAdmin(@PathVariable Long id, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        if (isAdmin == null || !isAdmin) {
            return "redirect:/login";
        }

        reservationRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }
}
