package com.rebellion.skillsync.controller;

import org.modelmapper.ModelMapper;
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
import com.rebellion.skillsync.dto.RegisterDto;
import com.rebellion.skillsync.model.entity.User;
import com.rebellion.skillsync.service.impl.UserServiceImpl;

@Controller
@RequestMapping("")
public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthController(UserServiceImpl userServiceImpl, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register/save")
    public ResponseEntity<?> postSignUp(@RequestBody RegisterDto input) {
        User foundUser = userServiceImpl.getUserByEmail(input.getEmail());
        if(foundUser == null) {
            userServiceImpl.saveUserToDb(modelMapper.map(input, User.class));
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();     
    }

    @PostMapping("/login/auth")
    public ResponseEntity<?> postLogin(@RequestBody LoginDto input) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
            return ResponseEntity.status(HttpStatus.FOUND).build();
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
