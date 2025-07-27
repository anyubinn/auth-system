package com.example.authsystem.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.example.authsystem.exception.CustomException;
import com.example.authsystem.exception.ErrorCode;
import com.example.authsystem.model.dto.response.AdminRoleResponse;
import com.example.authsystem.model.entity.User;
import com.example.authsystem.model.entity.UserRole;
import com.example.authsystem.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AdminService adminService;

    @Test
    @DisplayName("관리자 권한 변경 성공")
    void changeRole_success() {
        User target = new User("user", "pw", "사용자", UserRole.USER);
        when(userRepository.findById(2L)).thenReturn(Optional.of(target));

        AdminRoleResponse response = adminService.changeRole(2L);

        assertThat(response.userRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    @DisplayName("존재하지 않는 사용자에 대해 권한 변경 시도 시 예외 발생")
    void changeRole_userNotFound() {
        when(userRepository.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.changeRole(100L))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }
}