package com.example.authsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final ErrorDetail error;

    @Getter
    @AllArgsConstructor
    public static class ErrorDetail {
        private final String code;
        private final String message;
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(new ErrorDetail(errorCode.getCode(), errorCode.getMessage()));
    }
}
