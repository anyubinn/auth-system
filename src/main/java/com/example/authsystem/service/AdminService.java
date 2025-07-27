package com.example.authsystem.service;

import com.example.authsystem.exception.CustomException;
import com.example.authsystem.exception.ErrorCode;
import com.example.authsystem.model.dto.response.AdminRoleResponse;
import com.example.authsystem.model.entity.User;
import com.example.authsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public AdminRoleResponse changeRole(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.changeRole();

        return AdminRoleResponse.toDto(user);
    }
}
