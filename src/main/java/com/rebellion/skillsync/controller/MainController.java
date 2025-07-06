package com.rebellion.skillsync.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("")
public class MainController {

    @GetMapping("/")
    public ModelAndView getHomePage(@AuthenticationPrincipal UserDetails userDetails, ModelAndView modelAndView){
        if (!userDetails.getAuthorities().isEmpty()) {
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            if (role.equals("ROLE_CANDIDATE")) {
                modelAndView.setViewName("redirect:/candidate/dashboard");
            } else if(role.equals("ROLE_EMPLOYER")){
                modelAndView.setViewName("redirect:/employer/dashboard");
            } else if(role.equals("ROLE_ADMIN")) {
                modelAndView.setViewName("redirect:/admin/dashboard");
            }
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
}
