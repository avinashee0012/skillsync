package com.rebellion.skillsync.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rebellion.skillsync.model.entity.Application;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, Long> {
}
