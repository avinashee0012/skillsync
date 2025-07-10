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
public class LoginResponseDto {
    private Long userId;
    private String email;
    private Role role;
    private String message;
}
