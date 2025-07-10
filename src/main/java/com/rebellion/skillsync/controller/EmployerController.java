package com.rebellion.skillsync.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employer")
public class EmployerController {

    @GetMapping("/profile")
    public String testEmployerAccess(){
        return "Employer profile accessed";
    }
}
