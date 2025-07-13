package com.rebellion.skillsync.service;

import com.rebellion.skillsync.dto.CandidateProfileDto;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CandidateService {
    CandidateProfileDto getProfile(Long userId);
    CandidateProfileDto updateProfile(Long userId, CandidateProfileDto dto);
    String saveResumeToFS(Long userId, MultipartFile file);
    Resource downloadResumeFromFS(Long userId);
    HttpStatus deleteResumeFromFS(Long userId);
}
