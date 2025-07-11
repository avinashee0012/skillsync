package com.rebellion.skillsync.dto;

import com.rebellion.skillsync.model.enums.Degree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProfileDto {
    private Long id;
    private String fname; // from User
    private String lname; // from User
    private String githubUrl;
    private String linkedinUrl;
    private Degree degree;
    private Integer experience;
    private String resumePath;
    private String professionalSummary;
    List<String> skills; // from Candidate_Skills
}
