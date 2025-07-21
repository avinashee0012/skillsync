package com.rebellion.skillsync.controller;

import com.rebellion.skillsync.dto.ApplicationRequestDto;
import com.rebellion.skillsync.dto.ApplicationResponseDto;
import com.rebellion.skillsync.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<?> applyToJob(@RequestBody ApplicationRequestDto request, @AuthenticationPrincipal UserDetails userDetails
    ) {
        ResponseEntity<?> response = applicationService.makeAnApplication(userDetails.getUsername(), request.getJobId());
        return response;
    }
}
