package com.example.authsystem.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex,
        HttpServletResponse response) {
        response.setStatus(ErrorCode.BAD_REQUEST.getStatus().value());

        return ErrorResponse.from(ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGeneralException(Exception ex, HttpServletResponse response) {
        response.setStatus(ErrorCode.INTERNAL_ERROR.getStatus().value());

        return ErrorResponse.from(ErrorCode.INTERNAL_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ErrorResponse handleCustomException(CustomException ex, HttpServletResponse response) {
        ErrorCode errorCode = ex.getErrorCode();
        response.setStatus(errorCode.getStatus().value());

        return ErrorResponse.from(errorCode);
    }
}
