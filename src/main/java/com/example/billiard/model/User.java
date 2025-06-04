package com.example.billiard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")  // "user"는 예약어이므로 테이블 이름 지정
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private boolean isAdmin;
    private String name; // 👈 여기 추가!

    // --- Getter / Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }  // 👈 추가
    public void setName(String name) { this.name = name; }  // 👈 추가

    // ✅ 관리자 여부 Getter/Setter
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
