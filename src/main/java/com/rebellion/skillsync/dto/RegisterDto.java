package com.rebellion.skillsync.dto;

import java.time.LocalDate;
import com.rebellion.skillsync.model.enums.Role;
import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String email;
    private Long mobile;
    private LocalDate dob;
    private Role role;
    private String password;
}
