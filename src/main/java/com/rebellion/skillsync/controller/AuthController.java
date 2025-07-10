package com.rebellion.skillsync.controller;

import com.rebellion.skillsync.dto.LoginRequestDto;
import com.rebellion.skillsync.dto.LoginResponseDto;
import com.rebellion.skillsync.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.authenticateUser(request);
        return ResponseEntity.ok(response);
    }
}
