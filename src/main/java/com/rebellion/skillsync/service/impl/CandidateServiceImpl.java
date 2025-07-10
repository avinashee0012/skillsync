package com.rebellion.skillsync.service.impl;

import com.rebellion.skillsync.dto.CandidateProfileDto;
import com.rebellion.skillsync.model.entity.Candidate;
import com.rebellion.skillsync.repo.CandidateRepo;
import com.rebellion.skillsync.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final ModelMapper modelMapper;

    @Override
    public CandidateProfileDto getProfile(Long userId) {
        //  find candidate by userId
        Candidate candidate = candidateRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("No candidate with userId: " + userId));

        // map and return CandidateProfileDto
        return modelMapper.map(candidate, CandidateProfileDto.class);
    }

    @Override
    public CandidateProfileDto updateProfile(Long userId, CandidateProfileDto request) {
        //  find candidate by userId
        Candidate candidate = candidateRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("No candidate with userId: " + userId));

        //  modify candidate
        candidate.setDegree(request.getDegree());
        candidate.setExperience(request.getExperience());
        candidate.setGithubUrl(request.getGithubUrl());
        candidate.setLinkedinUrl(request.getLinkedinUrl());
        candidate.setResumePath(request.getResumePath());
        candidate.setProfessionalSummary(request.getProfessionalSummary());

        // save updated candidate
        Candidate updatedCandidate = candidateRepo.save(candidate);

        //  map and return CandidateProfileDto
        return modelMapper.map(updatedCandidate, CandidateProfileDto.class);
    }
}
