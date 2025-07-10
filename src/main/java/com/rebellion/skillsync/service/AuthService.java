package com.rebellion.skillsync.service;


import com.rebellion.skillsync.dto.LoginRequestDto;
import com.rebellion.skillsync.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto authenticateUser(LoginRequestDto request);
}
