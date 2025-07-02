package com.rebellion.skillsync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebellion.skillsync.model.entity.User;
import com.rebellion.skillsync.service.impl.UserServiceImpl;

@RestController
@RequestMapping("api")
public class PublicApiController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public PublicApiController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    
    @PostMapping("/sign-up/save")
    public ResponseEntity<User> postSignUp(@RequestBody User input){
        return ResponseEntity.ok(userServiceImpl.saveUserToDb(input));
    }
}
