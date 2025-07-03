package com.rebellion.skillsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {

    @GetMapping("/")
    public String getHomePage(){
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    // This page should be hidden behind login
    @GetMapping("/secret")
    public String getSecretPage(){
        return "secret";
    }
}
