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

    // 내가 쓴 리뷰 보기
    @GetMapping("/my-reviews")
    public String myReviews(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Review> myReviews = reviewRepository.findByUserEmail(user.getEmail());
        model.addAttribute("reviews", myReviews);
        return "myReviews";
    }

    // 전체 리뷰 보기 (사용자 전용 리스트)
    @GetMapping("/reviews")
    public String listAllReviews(Model model) {
        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "reviewList";
    }
}
