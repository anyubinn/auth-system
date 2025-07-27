package com.example.authsystem.model.entity;

import com.example.authsystem.exception.CustomException;
import com.example.authsystem.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum UserRole {
    USER, ADMIN;

    @JsonCreator
    public static UserRole parsing(String inputValue) {
        return Stream.of(UserRole.values())
            .filter(role -> role.name().equalsIgnoreCase(inputValue))
            .findFirst()
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ROLE));
    }
}
