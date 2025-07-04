package com.rebellion.skillsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/employer")
public class EmployerController {
    
    @GetMapping("/dashboard")
    public String getEmployerDashboard() {
        return "employer-dashboard";
    }
    
}
