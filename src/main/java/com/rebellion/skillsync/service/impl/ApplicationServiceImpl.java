package com.rebellion.skillsync.service.impl;

import com.rebellion.skillsync.dto.ApplicationResponseDto;
import com.rebellion.skillsync.model.entity.Application;
import com.rebellion.skillsync.model.entity.Candidate;
import com.rebellion.skillsync.model.entity.Job;
import com.rebellion.skillsync.model.enums.Status;
import com.rebellion.skillsync.repo.ApplicationRepo;
import com.rebellion.skillsync.repo.CandidateRepo;
import com.rebellion.skillsync.repo.JobRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rebellion.skillsync.service.ApplicationService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final CandidateRepo candidateRepo;
    private final JobRepo jobRepo;
    private final ApplicationRepo applicationRepo;

    private Candidate getCandidateByEmail(String candidateEmail){
        Candidate candidate = candidateRepo.findByUserEmail(candidateEmail).orElse(null);
        return candidate;
    }

    private Job getJobById(Long jobId){
        Job job = jobRepo.findById(jobId).orElse(null);
        return job;
    }

    @Override
    public ResponseEntity<?> makeAnApplication(String candidateEmail, Long jobId) {
        // find candidate
        Candidate candidate = this.getCandidateByEmail(candidateEmail); // already verified at controller --> this shouldn't be null

        // find job
        Job job = this.getJobById(jobId);
        if(job == null) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Contact customer support");
        }

        // if application already exists for candidate + job ==> throw exception
        if(applicationRepo.existsByCandidateAndJob(candidate, job)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Already applied");
        }

        // create application
        Application application = Application.builder()
                .candidate(candidate)
                .job(job)
                .appliedDate(LocalDate.now())
                .lastUpdateDate(LocalDate.now())
                .status(Status.APPLIED)
                .build();

        // save application
        applicationRepo.save(application);

        // map Application to ApplicationResponseDto
        ApplicationResponseDto response = ApplicationResponseDto.builder()
                .applicationId(application.getId())
                .jobId(application.getJob().getId())
                .jobTitle(application.getJob().getTitle())
                .appliedDate(application.getAppliedDate())
                .lastUpdateDate(application.getLastUpdateDate())
                .status(application.getStatus())
                .build();

        // return ApplicationResponseDto
        return ResponseEntity.ok(response);
    }
}
