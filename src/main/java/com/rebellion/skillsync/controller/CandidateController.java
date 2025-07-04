package com.rebellion.skillsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/candidate")
public class CandidateController {
    
    @GetMapping("/dashboard")
    public String getCandidateDashboard() {
        return "candidate-dashboard";
    }
    
}
