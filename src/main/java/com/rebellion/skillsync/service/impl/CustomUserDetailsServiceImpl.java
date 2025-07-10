package com.rebellion.skillsync.service.impl;

import com.rebellion.skillsync.model.entity.User;
import com.rebellion.skillsync.repo.UserRepo;
import com.rebellion.skillsync.security.CustomUserDetails;
import com.rebellion.skillsync.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //  find user from DB
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        //  return user
        return new CustomUserDetails(user);
    }
}
