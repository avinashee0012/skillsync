package com.rebellion.skillsync.service;

import java.util.List;

import com.rebellion.skillsync.dto.JobRequestDto;
import com.rebellion.skillsync.dto.JobResponseDto;
import com.rebellion.skillsync.model.enums.EmploymentType;
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

    List<JobResponseDto> getFilteredJobs(EmploymentType jobType, List<String> skills, String location);

    List<JobResponseDto> getOptimizedFilteredJobs(EmploymentType jobType, List<String> skills, String location);

}
