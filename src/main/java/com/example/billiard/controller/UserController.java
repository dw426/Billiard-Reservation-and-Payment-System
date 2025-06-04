package com.example.billiard.controller;
import com.example.billiard.model.User;
import com.example.billiard.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 로그인 폼 ("/", "/login" 둘 다 처리)
    @GetMapping({"/", "/login"})
    public String loginForm() {
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // ✅ 관리자 로그인
        if ("host".equals(email) && "1234".equals(password)) {
            session.setAttribute("isAdmin", true);
            return "redirect:/admin/dashboard";
        }

        // ✅ 일반 사용자 로그인
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                return "redirect:/main";
            }
        }

        model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
        return "login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // 회원가입 폼
    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String name,
                         Model model) {

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "이미 가입된 이메일입니다.");
            return "signup";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        userRepository.save(user);

        return "redirect:/login";
    }

    // 메인 페이지
    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("userName", user.getName());
        return "main";
    }
}
