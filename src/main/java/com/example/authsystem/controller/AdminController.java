package com.example.authsystem.controller;

import com.example.authsystem.exception.ErrorResponse;
import com.example.authsystem.model.dto.response.AdminRoleResponse;
import com.example.authsystem.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Operation(
        summary = "유저 권한 변경",
        description = "특정 유저의 권한을 USER ↔ ADMIN으로 변경합니다."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "권한 변경 성공", content = @Content(schema = @Schema(implementation = AdminRoleResponse.class))),
        @ApiResponse(responseCode = "404", description = "해당 사용자가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/users/{userId}/roles")
    public ResponseEntity<AdminRoleResponse> changeRole(@PathVariable Long userId) {

        AdminRoleResponse responseDto = adminService.changeRole(userId);

        return ResponseEntity.ok(responseDto);
    }
}
