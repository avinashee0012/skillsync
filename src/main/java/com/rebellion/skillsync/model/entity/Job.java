package com.rebellion.skillsync.model.entity;

import java.time.LocalDate;
import com.rebellion.skillsync.model.enums.EmploymentType;
import com.rebellion.skillsync.model.enums.WorkModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "jobs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String description;

    private String companyLocation;

    @Enumerated(EnumType.STRING)
    private WorkModel workModel;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    private LocalDate postedDate;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;

}
