package com.rebellion.skillsync.service.impl;

import com.rebellion.skillsync.dto.JobRequestDto;
import com.rebellion.skillsync.dto.JobResponseDto;
import com.rebellion.skillsync.model.entity.Employer;
import com.rebellion.skillsync.model.entity.Job;
import com.rebellion.skillsync.model.entity.JobSkill;
import com.rebellion.skillsync.model.entity.Skill;
import com.rebellion.skillsync.repo.EmployerRepo;
import com.rebellion.skillsync.repo.JobRepo;
import com.rebellion.skillsync.repo.JobSkillRepo;
import com.rebellion.skillsync.repo.SkillRepo;
import com.rebellion.skillsync.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepo jobRepo;
    private final EmployerRepo employerRepo;
    private final SkillRepo skillRepo;
    private final JobSkillRepo jobSkillRepo;

    private Employer getEmployerById(Long employerId) {
        Employer employer = employerRepo.findById(employerId).orElse(null);
        return employer;
    }

    @Override
    public JobResponseDto saveJobToDb(Long employerId, JobRequestDto request) {
        // find employer
        Employer employer = this.getEmployerById(employerId);
        if (employer == null) {
            return null;
        }

        // jobRequestDto to job mapping
        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .companyLocation(request.getCompanyLocation())
                .workModel(request.getWorkModel())
                .employmentType(request.getEmploymentType())
                .postedDate(LocalDate.now())
                .employer(employer)
                .build();

        // Create and add JobSkill links
        List<JobSkill> jobSkills = new ArrayList<>();
        for (String skillName : request.getRequiredSkills()) {
            Skill skill = skillRepo.findByNameIgnoreCase(skillName)
                    .orElseGet(() -> skillRepo.save(new Skill(skillName)));

            JobSkill jobSkill = new JobSkill();
            jobSkill.setJob(job);
            jobSkill.setSkill(skill);
            jobSkills.add(jobSkill);
        }
        job.setSkills(jobSkills);

        // save job to db
        Job savedJob = jobRepo.save(job);

        // job to jobResponseDto mapping
        JobResponseDto response = JobResponseDto.builder()
                .id(savedJob.getId())
                .title(savedJob.getTitle())
                .description(savedJob.getDescription())
                .companyLocation(savedJob.getCompanyLocation())
                .workModel(savedJob.getWorkModel())
                .employmentType(savedJob.getEmploymentType())
                .postedDate(savedJob.getPostedDate())
                .requiredSkills(request.getRequiredSkills())
                .companyName(savedJob.getEmployer().getCompanyName())
                .build();

        // return jobResponseDto
        return response;
    }

    @Override
    public List<JobResponseDto> getJobsByEmployer(Long employerId) {
        // get jobs by employer
        List<Job> jobs = jobRepo.findByEmployerId(employerId);

        // map job to jobResponseDto
        List<JobResponseDto> response = jobs.stream()
                .map(job -> {

                    // get skills for each job
                    List<String> skills = job.getSkills().stream()
                            .map(jobSkill -> jobSkill.getSkill().getName())
                            .collect(Collectors.toList());

                    return JobResponseDto.builder()
                            .id(job.getId())
                            .title(job.getTitle())
                            .description(job.getDescription())
                            .companyLocation(job.getCompanyLocation())
                            .workModel(job.getWorkModel())
                            .employmentType(job.getEmploymentType())
                            .postedDate(job.getPostedDate())
                            .requiredSkills(skills)
                            .companyName(job.getEmployer().getCompanyName())
                            .build();
                }).collect(Collectors.toList());

        // return jobResponseDto
        return response;
    }

    @Override
    public JobResponseDto updateJobInDb(Long jobId, JobRequestDto request) {
        // fetch jobs by employer
        Job job = jobRepo.findById(jobId).orElse(null);
        if (job == null) {
            return null;
        }

        // update job details
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompanyLocation(request.getCompanyLocation());
        job.setWorkModel(request.getWorkModel());
        job.setEmploymentType(request.getEmploymentType());

        // clear old job_skills
        job.getSkills().clear();
        jobSkillRepo.deleteByJobId(jobId);

        // create new job_skills
        for (String skillName : request.getRequiredSkills()) {
            Skill skill = skillRepo.findByNameIgnoreCase(skillName)
                    .orElseGet(() -> skillRepo.save(new Skill(skillName)));

            JobSkill jobSkill = new JobSkill();
            jobSkill.setJob(job);
            jobSkill.setSkill(skill);
            job.getSkills().add(jobSkill);
        }

        // save update job
        Job updatedJob = jobRepo.save(job);

        // map job to JobResponseDto
        JobResponseDto response = JobResponseDto.builder()
                .id(updatedJob.getId())
                .title(updatedJob.getTitle())
                .description(updatedJob.getDescription())
                .companyLocation(updatedJob.getCompanyLocation())
                .workModel(updatedJob.getWorkModel())
                .employmentType(updatedJob.getEmploymentType())
                .postedDate(updatedJob.getPostedDate())
                .requiredSkills(request.getRequiredSkills())
                .companyName(updatedJob.getEmployer().getCompanyName())
                .build();

        // return JobResponseDto
        return response;
    }

    @Override
    public HttpStatus deleteJobFromDb(Long jobId) {
        // find job
        Job job = jobRepo.findById(jobId).orElse(null);
        if (job == null) {
            return HttpStatus.BAD_REQUEST;
        }
        // delete associated jobSkills
        jobSkillRepo.deleteByJobId(jobId);
        // delete job
        jobRepo.deleteById(jobId);
        return HttpStatus.NO_CONTENT;
    }

    @Override
    public List<JobResponseDto> getFilteredJobs(String jobType, List<String> skills, String location) {

        // fetch all jobs
        List<Job> allJobs = jobRepo.findAll();

        // filter jobs
        List<JobResponseDto> filteredJobs = allJobs.stream().map(job -> JobResponseDto.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .description(job.getDescription())
                        .companyLocation(job.getCompanyLocation())
                        .workModel(job.getWorkModel())
                        .employmentType(job.getEmploymentType())
                        .postedDate(job.getPostedDate())
                        .companyName(job.getEmployer().getCompanyName())
                        .requiredSkills(job.getSkills().stream()
                                .map(jobSkill -> jobSkill.getSkill().getName())
                                .collect(Collectors.toList())
                        )
                        .build()
                )
                .filter(dto -> jobType == null || dto.getEmploymentType().name().equalsIgnoreCase(jobType))
                .filter(dto -> location == null || dto.getCompanyLocation().equalsIgnoreCase(location))
                .filter(dto -> {
                    if (skills == null || skills.isEmpty()) {
                        return true;
                    }
                    List<String> listOfSkills = dto.getRequiredSkills().stream()
                            .map(s -> s.toLowerCase()).collect(Collectors.toList());
                    return skills.stream().map(skill -> skill.toLowerCase())
                            .allMatch(lowerCaseSkill -> listOfSkills.contains(lowerCaseSkill));
                })
                .collect(Collectors.toList());

        // return List<JobResponseDto>
        return filteredJobs;
    }
}
