package com.rebellion.skillsync.model.entity;

import java.time.LocalDate;
import java.util.Set;

import com.rebellion.skillsync.model.enums.Degree;
import com.rebellion.skillsync.model.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String name;
    private String email;
    private Long mobile;
    private LocalDate dob;
    @Column(length = 2048)
    private String bio;
    private Role role;
    private String password;
    private Degree degree;
    private Integer experience;
    private String location;

    @ManyToMany
    @JoinTable (
        name = "user_skills",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;
    
    @OneToMany
    @JoinColumn(name = "job_id")
    private Set<Job> jobs;

    @OneToMany
    @JoinColumn(name = "application_id")
    private Set<Application> applications;
}
