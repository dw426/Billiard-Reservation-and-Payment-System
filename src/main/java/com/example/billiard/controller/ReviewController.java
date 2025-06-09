package com.example.billiard.controller;

import com.example.billiard.model.Review;
import com.example.billiard.model.User;
import com.example.billiard.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    // 리뷰 작성 폼
    @GetMapping("/review/form")
    public String showReviewForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("review", new Review());
        return "reviewForm";
    }

    // 리뷰 저장 처리
    @PostMapping("/review")
    public String submitReview(@ModelAttribute Review review, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        review.setUserEmail(user.getEmail());
        reviewRepository.save(review);

        return "redirect:/my-reviews";
    }

    // 전체 리뷰 리스트 (모든 사용자 확인 가능)
    @GetMapping("/reviews")
    public String listAllReviews(Model model) {
        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "reviewList";
    }

    // 내가 쓴 리뷰 보기
    @GetMapping("/my-reviews")
    public String myReviews(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Review> myReviews = reviewRepository.findByUserEmail(user.getEmail());
        model.addAttribute("reviews", myReviews);
        return "myReviews";
    }

    // 관리자 - 모든 리뷰 관리
    @GetMapping("/admin/reviews")
    public String adminReviewList(Model model, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) return "redirect:/login";

        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "adminReviewList";
    }

    // 관리자 - 리뷰 삭제
    @PostMapping("/admin/reviews/delete/{id}")
    public String deleteReviewAsAdmin(@PathVariable Long id, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) return "redirect:/login";

        reviewRepository.deleteById(id);
        return "redirect:/admin/reviews";
    }
}
