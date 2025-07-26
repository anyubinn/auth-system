package com.example.authsystem.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class User {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private UserRole role;

    public User(String username, String password, String nickname, UserRole role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static User toEntity(String username, String password, String nickname, UserRole role) {
        return new User(username, password, nickname, role);
    }
}
