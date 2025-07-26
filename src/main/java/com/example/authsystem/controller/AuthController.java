package com.example.authsystem.controller;

import com.example.authsystem.model.dto.request.LoginRequest;
import com.example.authsystem.model.dto.request.SignUpRequest;
import com.example.authsystem.model.dto.response.LoginResponse;
import com.example.authsystem.model.dto.response.SignUpResponse;
import com.example.authsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest requestDto) {

        SignUpResponse responseDto = authService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest requestDto) {

        LoginResponse responseDto = authService.login(requestDto);

        return ResponseEntity.ok(responseDto);
    }
}
