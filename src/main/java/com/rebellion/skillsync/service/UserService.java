package com.rebellion.skillsync.service;

import java.util.List;

import com.rebellion.skillsync.model.entity.User;

public interface UserService {
    // Create
    User saveUserToDb(User user);
    
    // Read
    User getUserByEmail(String email);
    User getUserById(Long userId);
    List<User> getAllUsers();

    // Update
    User updateUserById(Long userId);
    
    // Delete
    void deleteUserById(Long userId);
}
