package com.rebellion.skillsync.dto;

import com.rebellion.skillsync.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String fname;
    private String lname;
    private String email;
    private LocalDate dob;
    private Role role;
}
