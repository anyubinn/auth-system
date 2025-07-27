package com.example.authsystem.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.authsystem.exception.CustomException;
import com.example.authsystem.exception.ErrorCode;
import com.example.authsystem.model.dto.request.LoginRequest;
import com.example.authsystem.model.dto.request.SignUpRequest;
import com.example.authsystem.model.dto.response.LoginResponse;
import com.example.authsystem.model.dto.response.SignUpResponse;
import com.example.authsystem.model.entity.User;
import com.example.authsystem.model.entity.UserRole;
import com.example.authsystem.repository.UserRepository;
import com.example.authsystem.security.CustomPasswordEncoder;
import com.example.authsystem.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CustomPasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        SignUpRequest request = new SignUpRequest("user1", "pw", "닉네임", UserRole.USER);
        when(userRepository.existsByUsername("user1")).thenReturn(false);
        when(passwordEncoder.encode("pw")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(
            invocation -> invocation.getArgument(0));

        SignUpResponse response = authService.signup(request);

        assertThat(response.username()).isEqualTo("user1");
        assertThat(response.userRole()).isEqualTo(UserRole.USER);
    }

    @Test
    @DisplayName("중복 사용자로 인해 회원가입 실패")
    void signup_duplicate() {
        SignUpRequest request = new SignUpRequest("user1", "pw", "닉네임", UserRole.USER);
        when(userRepository.existsByUsername("user1")).thenReturn(true);

        assertThatThrownBy(() -> authService.signup(request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.USER_ALREADY_EXISTS.getMessage());
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        LoginRequest request = new LoginRequest("user1", "pw");
        User user = new User("user1", "encodePw", "닉네임", UserRole.USER);
        when(userRepository.findByUsername("user1")).thenReturn(user);
        when(passwordEncoder.matches("pw", "encodePw")).thenReturn(true);
        when(jwtTokenProvider.generateToken("user1")).thenReturn("mocked.jwt.token");

        LoginResponse response = authService.login(request);

        assertThat(response.token()).isEqualTo("mocked.jwt.token");
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 실패")
    void login_invalid_password() {
        LoginRequest request = new LoginRequest("user1", "wrongpw");
        User user = new User("user1", "encodePw", "닉네임", UserRole.USER);
        when(userRepository.findByUsername("user1")).thenReturn(user);
        when(passwordEncoder.matches("wrongpw", "encodePw")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.INVALID_CREDENTIALS.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 로그인 실패")
    void login_user_not_found() {
        LoginRequest request = new LoginRequest("user2", "pw");
        when(userRepository.findByUsername("user2")).thenReturn(null);

        assertThatThrownBy(() -> authService.login(request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.INVALID_CREDENTIALS.getMessage());
    }
}