package com.rebellion.skillsync.service;


import com.rebellion.skillsync.dto.ApplicationResponseDto;
import org.springframework.http.ResponseEntity;

public interface ApplicationService {
    // Create
    ResponseEntity<?> makeAnApplication(String candidateEmail, Long jobId);

    // Read

    // Update
    
    // Delete
}
