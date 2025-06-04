package com.example.billiard.controller;

import com.example.billiard.model.User;
import com.example.billiard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirm,
                           Model model) {
        if (!password.equals(confirm)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "register";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // 실제 서비스에서는 암호화 필요

        userRepository.save(user);
        model.addAttribute("message", "회원가입이 완료되었습니다!");
        return "register";
    }
}
