package com.rebellion.skillsync.service;

import com.rebellion.skillsync.dto.UserDto;
import com.rebellion.skillsync.dto.UserRegisterDto;
import com.rebellion.skillsync.dto.UserResponseDto;

public interface UserService {
    // Create
    UserResponseDto registerUser(UserRegisterDto user);

    // Read
    UserDto getUserById(Long id);

    // Update
    // Delete
}
