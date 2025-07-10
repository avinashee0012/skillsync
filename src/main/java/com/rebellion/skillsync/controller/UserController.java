package com.rebellion.skillsync.controller;

import com.rebellion.skillsync.dto.UserRegisterDto;
import com.rebellion.skillsync.dto.UserResponseDto;
import com.rebellion.skillsync.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegisterDto request){
        UserResponseDto response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }
}
