package com.rebellion.skillsync.dto;

import com.rebellion.skillsync.model.enums.Degree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProfileDto {
    private Long id;
    private String githubUrl;
    private String linkedinUrl;
    private Degree degree;
    private Integer experience;
    private String resumePath;
    private String professionalSummary;
}
