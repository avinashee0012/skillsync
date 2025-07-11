package com.rebellion.skillsync.service.impl;

import com.rebellion.skillsync.dto.LoginRequestDto;
import com.rebellion.skillsync.dto.LoginResponseDto;
import com.rebellion.skillsync.model.entity.User;
import com.rebellion.skillsync.repo.UserRepo;
import com.rebellion.skillsync.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto authenticateUser(LoginRequestDto request) {
        //  find User by email
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid login credential"));
        //  if password NOT matches
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("invalid login credential");
        }

        //  map User to LoginResponseDto and return LoginResponseDto
        return LoginResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .message("Login successful.")
                .build();
    }
}
