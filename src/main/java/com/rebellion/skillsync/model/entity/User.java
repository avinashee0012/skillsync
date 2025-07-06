package com.rebellion.skillsync.model.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import com.rebellion.skillsync.model.enums.Degree;
import com.rebellion.skillsync.model.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
    private Long userId;
    private String name;
    @Column(unique = true)
    private String email;
    private Long mobile;
    private LocalDate dob;
    @Column(length = 2048)
    private String bio;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;
    @Enumerated(EnumType.STRING)
    private Degree degree;
    private Integer experience;
    private String location;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable (
        name = "user_skill",
        joinColumns = @JoinColumn(name = "userId"),
        inverseJoinColumns = @JoinColumn(name = "skillId")
    )
    private Set<Skill> haveSkills; // User can have multiple uique skills
    
    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL)
    private List<Job> postedJobs; // User can post multiple jobs

    @OneToMany(mappedBy = "byUser", cascade = CascadeType.ALL)
    private List<Application> applicationsMade; // User can make multiple applications
}
