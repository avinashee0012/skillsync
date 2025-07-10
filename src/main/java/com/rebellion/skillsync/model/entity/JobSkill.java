package com.rebellion.skillsync.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_skills", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"job_id", "skill_id"})
    })
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // many-to-many relationship
    @ManyToOne // One Job --> Many JobSkills
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne // One Skill --> Many JobSkills
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}
