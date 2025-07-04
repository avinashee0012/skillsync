package com.rebellion.skillsync.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rebellion.skillsync.model.entity.User;
import com.rebellion.skillsync.repo.UserRepo;
import com.rebellion.skillsync.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUserToDb(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User getUserByEmail(String email) {
        Optional<User> userFromDB = userRepo.findByEmail(email);
        if(userFromDB.isPresent()){
            return userFromDB.get();
        }
        return null;
    }
    
}
