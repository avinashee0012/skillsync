package com.rebellion.skillsync.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "candidate_skills", uniqueConstraints = {   // composite key declaration
        @UniqueConstraint(columnNames = {"candidate_id", "skill_id"})
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Defining many-to-many relation between candidate and skill

    @ManyToOne // One Candidate --> Many CandidateSkill entry
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne // One Skill --> Many CandidateSkill entry
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}
