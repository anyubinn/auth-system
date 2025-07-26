package com.example.authsystem.model.dto.response;

import com.example.authsystem.model.entity.User;
import com.example.authsystem.model.entity.UserRole;

public record AdminRoleResponse(
    String username,
    String nickname,
    UserRole userRole
) {

    public static AdminRoleResponse toDto(User user) {
        return new AdminRoleResponse(user.getUsername(), user.getNickname(), user.getRole());
    }
}
