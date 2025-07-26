package com.example.authsystem.model.dto.response;

import com.example.authsystem.model.entity.User;
import com.example.authsystem.model.entity.UserRole;

public record SignUpResponse(
    String username,
    String nickname,
    UserRole userRole
) {

    public static SignUpResponse toDto(User user) {
        return new SignUpResponse(user.getUsername(), user.getNickname(), user.getRole());
    }
}
