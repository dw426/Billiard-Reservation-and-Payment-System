package com.example.billiard.service;

import com.example.billiard.model.User;
import com.example.billiard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false; // 이미 존재
        }
        userRepository.save(user);
        return true;
    }

    public boolean login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(u -> u.getPassword().equals(password)).orElse(false);
    }
}
