package com.rebellion.skillsync.controller;

import com.rebellion.skillsync.dto.CandidateProfileDto;
import com.rebellion.skillsync.model.entity.Candidate;
import com.rebellion.skillsync.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    // Mocking userId for testing
    private static final Long USER_ID = 2L;

    @GetMapping("/profile")
    public ResponseEntity<CandidateProfileDto> getCandidateProfile(){
        CandidateProfileDto response = candidateService.getProfile(USER_ID);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<CandidateProfileDto> putCandidateProfile(@RequestBody CandidateProfileDto request){
        CandidateProfileDto response = candidateService.updateProfile(USER_ID, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resume")
    public ResponseEntity<String> uploadResume(@RequestParam Long userId, @RequestParam MultipartFile file){
        String message = candidateService.saveResumeToFS(userId, file);
        return ResponseEntity.ok(message);
    }

}
