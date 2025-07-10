package com.rebellion.skillsync.model.entity;

import java.time.LocalDate;
import com.rebellion.skillsync.model.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fname;

    private String lname;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDate dob;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // to store name instead of Ordinal Number
    @Column(nullable = false)
    private Role role;
}
