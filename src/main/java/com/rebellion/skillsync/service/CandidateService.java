package com.rebellion.skillsync.service;

import com.rebellion.skillsync.dto.CandidateProfileDto;

public interface CandidateService {
    CandidateProfileDto getProfile(Long userId);
    CandidateProfileDto updateProfile(Long userId, CandidateProfileDto dto);
}
