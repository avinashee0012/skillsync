package com.rebellion.skillsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @GetMapping("/dashboard")
    public String getAdminDashboard() {
        return "admin-dashboard";
    }
}
