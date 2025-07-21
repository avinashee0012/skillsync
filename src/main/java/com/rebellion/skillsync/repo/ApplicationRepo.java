package com.rebellion.skillsync.repo;

import com.rebellion.skillsync.model.entity.Candidate;
import com.rebellion.skillsync.model.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rebellion.skillsync.model.entity.Application;

import java.util.List;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, Long> {
    boolean existsByCandidateAndJob(Candidate candidate, Job job);
    List<Application> findByCandidate(Candidate candidate);
}
