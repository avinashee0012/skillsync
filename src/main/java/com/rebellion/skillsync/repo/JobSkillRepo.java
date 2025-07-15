package com.rebellion.skillsync.repo;

import com.rebellion.skillsync.model.entity.Job;
import com.rebellion.skillsync.model.entity.JobSkill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSkillRepo extends JpaRepository<JobSkill, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM JobSkill js WHERE js.job.id = :jobId")
    void deleteByJobId(@Param("jobId") Long jobId);
}
