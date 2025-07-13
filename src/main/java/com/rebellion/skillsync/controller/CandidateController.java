package com.rebellion.skillsync.controller;

import com.rebellion.skillsync.dto.CandidateProfileDto;
import com.rebellion.skillsync.model.entity.Candidate;
import com.rebellion.skillsync.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
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

    @GetMapping("/resume")
    public ResponseEntity<?> downloadResume(@RequestParam Long userId) {
        Resource resource = candidateService.downloadResumeFromFS(userId);
        if(resource == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No resume found!");
        }
        int skipFirstUnderscore = resource.getFilename().indexOf('_') + 1;
        int skipSecondUnderscore = resource.getFilename().substring(skipFirstUnderscore).indexOf('_') + 1;
        String downloadFileName = resource.getFilename().substring(skipFirstUnderscore + skipSecondUnderscore);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadFileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/resume")
    public ResponseEntity<HttpStatus> deleteResume(@RequestParam Long userId) {
        HttpStatus status = candidateService.deleteResumeFromFS(userId);
        return ResponseEntity.status(status.value()).build();
    }

}
