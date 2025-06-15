package com.example.billiard.controller;

import com.example.billiard.model.Review;
import com.example.billiard.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/reviews")
public class AdminReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    // 관리자 리뷰 목록
    @GetMapping
    public String adminReviewList(Model model, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) return "redirect:/login";

        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "adminReviewList"; // templates/adminReviewList.html
    }

    // 관리자 리뷰 삭제
    @PostMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) return "redirect:/login";

        reviewRepository.deleteById(id);
        return "redirect:/admin/reviews";
    }
}
