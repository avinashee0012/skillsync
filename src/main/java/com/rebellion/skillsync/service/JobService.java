package com.rebellion.skillsync.service;

import java.util.List;

import com.rebellion.skillsync.dto.JobRequestDto;
import com.rebellion.skillsync.dto.JobResponseDto;
import org.springframework.http.HttpStatus;

public interface JobService {
    // Create
    JobResponseDto saveJobToDb(Long employerId, JobRequestDto job);

    // Read
    List<JobResponseDto> getJobsByEmployer(Long employerId);

    // Update
    JobResponseDto updateJobInDb(Long jobId, JobRequestDto dto);

    // Delete
    HttpStatus deleteJobFromDb(Long jobId);
}
