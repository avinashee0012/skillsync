package com.rebellion.skillsync.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rebellion.skillsync.dto.UserDto;
import com.rebellion.skillsync.model.entity.User;
import com.rebellion.skillsync.service.impl.UserServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    private final UserServiceImpl userServiceImpl;
    private final ModelMapper modelMapper;

    public CandidateController(UserServiceImpl userServiceImpl, ModelMapper modelMapper) {
        this.userServiceImpl = userServiceImpl;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/dashboard")
    public ModelAndView getCandidateDashboard(@AuthenticationPrincipal UserDetails userDetails,
            ModelAndView modelAndView) {
        User user = userServiceImpl.getUserByEmail(userDetails.getUsername());
        if (user != null) {
            modelAndView.setStatus(HttpStatus.OK);
            modelMapper.typeMap(User.class, UserDto.class);
            UserDto userDto = modelMapper.map(user, UserDto.class);
            modelAndView.addObject("user", userDto);
            modelAndView.setViewName("dashboard");
            return modelAndView;
        }
        modelAndView.setStatus(HttpStatus.FORBIDDEN);
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
}
