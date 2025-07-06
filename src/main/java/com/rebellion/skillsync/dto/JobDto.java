package com.rebellion.skillsync.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.rebellion.skillsync.model.entity.Application;
import com.rebellion.skillsync.model.entity.Skill;
import com.rebellion.skillsync.model.entity.User;

import lombok.Data;

@Data
public class JobDto {
    private Long jobId;
    private String title;
    private String description;
    private String company;
    private String workLocation;
    private Integer requiredExperience;
    private LocalDateTime postedAt;
    private User postedBy;
    private Set<Skill> requiredSkills;
    private Set<Application> applicationsReceived;
}
