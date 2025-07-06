package com.rebellion.skillsync.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import com.rebellion.skillsync.model.entity.Application;
import com.rebellion.skillsync.model.entity.Job;
import com.rebellion.skillsync.model.entity.Skill;
import com.rebellion.skillsync.model.enums.Degree;
import com.rebellion.skillsync.model.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String name;
    private String email;
    private Long mobile;
    private LocalDate dob;
    private String bio;
    private Role role;
    private Degree degree;
    private Integer experience;
    private String location;
    private Set<Skill> haveSkills;     
    private List<Job> postedJobs;
    private List<Application> applicationsMade;
}
