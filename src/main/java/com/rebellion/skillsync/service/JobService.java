package com.rebellion.skillsync.service;

import java.util.List;
import com.rebellion.skillsync.model.entity.Job;

public interface JobService {
    // Create
    Job saveJobToDb(Job job);
    
    // Read
    Job getJobById(Long jobId);
    List<Job> getAllJobs();

    // Update
    Job updateJobById(Long jobId);
    
    // Delete
    void deleteJobById(Long jobId);
}
