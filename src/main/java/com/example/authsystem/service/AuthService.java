package com.example.authsystem.service;

import com.example.authsystem.exception.CustomException;
import com.example.authsystem.exception.ErrorCode;
import com.example.authsystem.model.dto.request.LoginRequest;
import com.example.authsystem.model.dto.request.SignUpRequest;
import com.example.authsystem.model.dto.response.LoginResponse;
import com.example.authsystem.model.dto.response.SignUpResponse;
import com.example.authsystem.model.entity.User;
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

        if (userRepository.existsByUsername(requestDto.username())) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }

        String encodedPassword = customPasswordEncoder.encode(requestDto.password());
        User user = User.toEntity(requestDto.username(), encodedPassword, requestDto.nickname(), requestDto.userRole());
        User savedUser = userRepository.save(user);

        return SignUpResponse.toDto(savedUser);
    }

    public LoginResponse login(LoginRequest requestDto) {

        User user = userRepository.findByUsername(requestDto.username());

        if (user == null || !customPasswordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }
        String token = jwtTokenProvider.generateToken(user.getUsername());

        return LoginResponse.toDto(token);
    }
}
