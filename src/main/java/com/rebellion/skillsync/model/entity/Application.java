package com.rebellion.skillsync.model.entity;

import java.time.LocalDateTime;

import com.rebellion.skillsync.model.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User byUser;

    @ManyToOne
    @JoinColumn(name = "jobId", nullable = false)
    private Job forjob;

    private LocalDateTime appliedAt;
    @Enumerated(EnumType.STRING)
    private Status status;
}
