package com.rebellion.skillsync.service;

import com.rebellion.skillsync.dto.CandidateProfileDto;
import org.springframework.web.multipart.MultipartFile;

public interface CandidateService {
    CandidateProfileDto getProfile(Long userId);
    CandidateProfileDto updateProfile(Long userId, CandidateProfileDto dto);
    String saveResumeToFS(Long userId, MultipartFile file);
}
