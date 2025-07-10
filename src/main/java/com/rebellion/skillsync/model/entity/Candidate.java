package com.rebellion.skillsync.model.entity;

import com.rebellion.skillsync.model.enums.Degree;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private Integer experience; // In years

    @Enumerated(EnumType.STRING)
    private Degree degree;

    private String professionalSummary;

    private String githubUrl;

    private String linkedinUrl;

    private String resumePath;
}
