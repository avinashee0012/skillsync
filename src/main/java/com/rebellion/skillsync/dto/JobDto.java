package com.rebellion.skillsync.dto;

import com.rebellion.skillsync.model.enums.EmploymentType;
import com.rebellion.skillsync.model.enums.WorkModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDto {
    private Long id;
    private String title;
    private String description;
    private String companyLocation;
    private WorkModel workModel;
    private EmploymentType employmentType;
    private LocalDate postedDate;
    private Long employerId;
}
