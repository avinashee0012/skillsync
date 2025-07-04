package com.rebellion.skillsync.service;

import com.rebellion.skillsync.model.entity.User;

public interface UserService {
    // Create
    User saveUserToDb(User user);
    
    // Read
    User getUserByEmail(String email);

    // Update
    
    // Delete
}
