package com.rebellion.skillsync.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rebellion.skillsync.model.entity.Job;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {
}
