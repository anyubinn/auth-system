package com.example.authsystem.model.dto.request;

public record SignUpRequest(
    String username,
    String password,
    String nickname
) {

}
