package com.rebellion.skillsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PublicController {

    @GetMapping("/")
    public String getHomePage(){
        return "index";
    }

    @GetMapping("/sign-up")
    public String getSignupPage(){
        return "sign-up";
    }
}
