package com.rebellion.skillsync.repo;

import com.rebellion.skillsync.model.enums.EmploymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rebellion.skillsync.model.entity.Job;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {
    List<Job> findByEmployerId(Long employerId);

    @Query(
        """
            SELECT DISTINCT j FROM Job j
            LEFT JOIN j.skills js
            LEFT JOIN js.skill s
            WHERE (:jobType IS NULL OR j.employmentType = :jobType)
            AND (:location IS NULL OR j.companyLocation = :location)
            AND (:skills IS NULL OR LOWER(s.name) IN :skills)
        """
    )
    List<Job> findFilteredJobs(@Param("jobType") EmploymentType jobType,
                               @Param("skills") List<String> skills,
                               @Param("location") String location);

}
