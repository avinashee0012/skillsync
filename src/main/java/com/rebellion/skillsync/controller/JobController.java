package com.rebellion.skillsync.controller;

import com.rebellion.skillsync.dto.JobMatchDto;
import com.rebellion.skillsync.dto.JobRequestDto;
import com.rebellion.skillsync.dto.JobResponseDto;
import com.rebellion.skillsync.model.enums.EmploymentType;
import com.rebellion.skillsync.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping("/employer/{employerId}")
    public ResponseEntity<JobResponseDto> createJob(@PathVariable Long employerId, @RequestBody JobRequestDto request) {
        JobResponseDto response = jobService.saveJobToDb(employerId, request);
        if (response == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<JobResponseDto>> getJobs(@PathVariable Long employerId) {
        List<JobResponseDto> response = jobService.getJobsByEmployer(employerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponseDto> updateJob(@PathVariable Long jobId, @RequestBody JobRequestDto request) {
        JobResponseDto response = jobService.updateJobInDb(jobId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId) {
        HttpStatus response = jobService.deleteJobFromDb(jobId);
        return ResponseEntity.status(response).build();
    }

    @Deprecated
    @GetMapping("/filter")
    public ResponseEntity<List<JobResponseDto>> filterJobs(
            @RequestParam(required = false) EmploymentType jobType,
            @RequestParam(required = false) List<String> skills,
            @RequestParam(required = false) String location
        ) {
        List<JobResponseDto> response = jobService.getFilteredJobs(jobType, skills, location);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/optimized-filter")
    public ResponseEntity<List<JobResponseDto>> optimizedFilterJobs(
            @RequestParam(required = false) EmploymentType jobType,
            @RequestParam(required = false) List<String> skills,
            @RequestParam(required = false) String location
    ) {
        List<JobResponseDto> response = jobService.getOptimizedFilteredJobs(jobType, skills, location);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/candidates/{candidateId}/job-matches")
    public ResponseEntity<?> getTopMatches(@PathVariable Long candidateId) {
        List<JobMatchDto> matches = jobService.getTopMatchingJobsForCandidate(candidateId);
        if(matches == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Candidate not found");
        }
        return ResponseEntity.ok(matches);
    }
}
