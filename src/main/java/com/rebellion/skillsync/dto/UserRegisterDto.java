package com.rebellion.skillsync.dto;

import java.time.LocalDate;
import com.rebellion.skillsync.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    private String fname;
    private String lname;
    private String email;
    private LocalDate dob;
    private String password;
    private Role role;
}
