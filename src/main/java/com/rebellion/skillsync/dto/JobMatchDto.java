package com.rebellion.skillsync.dto;

import com.rebellion.skillsync.model.enums.EmploymentType;
import com.rebellion.skillsync.model.enums.WorkModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobMatchDto {
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private String companyLocation;
    private Integer matchScore; // out of 100
}
