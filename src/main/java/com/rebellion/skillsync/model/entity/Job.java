package com.rebellion.skillsync.model.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    private String title;
    @Column(length = 2048)
    private String description;
    private String company;
    private String workLocation;
    private Integer requiredExperience;
    private LocalDateTime postedAt;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User postedBy;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "job_skill",
        joinColumns = @JoinColumn(name = "jobId"),
        inverseJoinColumns = @JoinColumn(name = "skillId")
    )
    private Set<Skill> requiredSkills;

    @OneToMany(mappedBy = "forjob", cascade = CascadeType.ALL)
    private Set<Application> applicationsReceived;
}
