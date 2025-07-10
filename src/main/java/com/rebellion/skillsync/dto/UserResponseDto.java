package com.rebellion.skillsync.dto;

import com.rebellion.skillsync.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    // +id
    private Long id;
    private String fname;
    private String lname;
    private String email;
    // -password
    // -dob
    private Role role;
}
