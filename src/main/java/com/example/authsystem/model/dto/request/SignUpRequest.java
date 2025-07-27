package com.example.authsystem.model.dto.request;

import com.example.authsystem.model.entity.UserRole;

public record SignUpRequest(
    String username,
    String password,
    String nickname,
    UserRole userRole
) {

}
