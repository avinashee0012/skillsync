package com.rebellion.skillsync.model.entity;

import com.rebellion.skillsync.model.enums.Degree;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL) // allows the fname/lname to be modified via Candidate
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private Integer experience; // In years

    @Enumerated(EnumType.STRING)
    private Degree degree;

    private String professionalSummary;

    private String githubUrl;

    private String linkedinUrl;

    private String resumePath;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidateSkill> skills;
}
