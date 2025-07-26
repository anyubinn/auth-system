package com.example.authsystem.model.dto.response;

public record LoginResponse(
    String token
) {

    public static LoginResponse toDto (String token) {
        return new LoginResponse(token);
    }
}
