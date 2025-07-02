package com.rebellion.skillsync.model.entity;

import java.util.Set;

import com.rebellion.skillsync.model.enums.Proficiency;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Proficiency proficiency;

    @ManyToMany(mappedBy = "haveSkills")
    private Set<User> usersWithSkill;
    
    @ManyToMany(mappedBy = "requiredSkills")
    private Set<Job> jobsRequiringSkill;
}
