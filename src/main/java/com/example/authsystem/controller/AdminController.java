package com.example.authsystem.controller;

import com.example.authsystem.model.dto.response.AdminRoleResponse;
import com.example.authsystem.service.AdminService;
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

    @PatchMapping("/users/{userId}/roles")
    public ResponseEntity<AdminRoleResponse> changeRole(@PathVariable Long userId) {

        AdminRoleResponse responseDto = adminService.changeRole(userId);

        return ResponseEntity.ok(responseDto);
    }
}
