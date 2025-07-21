package com.rebellion.skillsync.dto;

import com.rebellion.skillsync.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDto {
    private Long applicationId;
    private Long jobId;
    private String jobTitle;
    private LocalDate appliedDate;
    private LocalDate lastUpdateDate;
    private Status status;
}
