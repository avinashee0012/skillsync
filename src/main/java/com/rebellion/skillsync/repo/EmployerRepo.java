package com.rebellion.skillsync.repo;

import com.rebellion.skillsync.model.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepo extends JpaRepository<Employer, Long> {
}
