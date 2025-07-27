package com.example.authsystem.controller;

import com.example.authsystem.exception.ErrorResponse;
import com.example.authsystem.model.dto.request.LoginRequest;
import com.example.authsystem.model.dto.request.SignUpRequest;
import com.example.authsystem.model.dto.response.LoginResponse;
import com.example.authsystem.model.dto.response.SignUpResponse;
import com.example.authsystem.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "회원가입", description = "username, password, nickname, userRole을 입력받아 회원가입을 진행합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = SignUpResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "이미 존재하는 사용자", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest requestDto) {

        SignUpResponse responseDto = authService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "로그인", description = "username과 password를 입력받아 JWT 토큰을 발급합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 올바르지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest requestDto) {

        LoginResponse responseDto = authService.login(requestDto);

        return ResponseEntity.ok(responseDto);
    }
}
