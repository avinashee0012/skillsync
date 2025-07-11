package com.rebellion.skillsync.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rebellion.skillsync.model.entity.Skill;

import java.util.Optional;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Long> {
    Optional<Skill> findByNameIgnoreCase(String skillName);
}
