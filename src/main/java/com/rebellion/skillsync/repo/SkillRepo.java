package com.rebellion.skillsync.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rebellion.skillsync.model.entity.Skill;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Long> {
}
