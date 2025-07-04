package com.rebellion.skillsync.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rebellion.skillsync.dto.LoginDto;
import com.rebellion.skillsync.model.entity.User;
import com.rebellion.skillsync.service.impl.UserServiceImpl;

@Controller
@RequestMapping("")
public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserServiceImpl userServiceImpl, AuthenticationManager authenticationManager) {
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register/save")
    public ResponseEntity<User> postSignUp(@RequestBody User input) {
        return ResponseEntity.ok(userServiceImpl.saveUserToDb(input));
    }

    @PostMapping("/login/auth")
    public ResponseEntity<String> postLogin(@RequestBody LoginDto input) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
            return ResponseEntity.ok("Login sucessful");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Login Credentials");
        }
    }
}
