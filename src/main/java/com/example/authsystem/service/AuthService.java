package com.example.authsystem.service;

import com.example.authsystem.model.dto.request.LoginRequest;
import com.example.authsystem.model.dto.request.SignUpRequest;
import com.example.authsystem.model.dto.response.LoginResponse;
import com.example.authsystem.model.dto.response.SignUpResponse;
import com.example.authsystem.model.entity.User;
import com.example.authsystem.model.entity.UserRole;
import com.example.authsystem.repository.UserRepository;
import com.example.authsystem.security.JwtTokenProvider;
import com.example.authsystem.security.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomPasswordEncoder customPasswordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public SignUpResponse signup(SignUpRequest requestDto) {

        String encodedPassword = customPasswordEncoder.encode(requestDto.password());
        User user = User.toEntity(requestDto.username(), encodedPassword, requestDto.nickname(), UserRole.USER);
        User savedUser = userRepository.save(user);

        return SignUpResponse.toDto(savedUser);
    }

    public LoginResponse login(LoginRequest requestDto) {

        User user = userRepository.findByUsername(requestDto.username());

        if (user == null || !customPasswordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        String token = jwtTokenProvider.generateToken(user.getUsername());

        return LoginResponse.toDto(token);
    }
}
