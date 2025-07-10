package com.rebellion.skillsync.repo;

import com.rebellion.skillsync.model.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByUserId(Long userId);
}
