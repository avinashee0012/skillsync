package com.rebellion.skillsync.repo;

import com.rebellion.skillsync.model.entity.CandidateSkill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateSkillRepo extends JpaRepository<CandidateSkill, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CandidateSkill cs WHERE cs.candidate.id = :candidateId")
    void deleteByCandidateId(@Param("candidateId") Long candidateId);
}
