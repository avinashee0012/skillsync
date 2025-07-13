package com.rebellion.skillsync.service.impl;

import com.rebellion.skillsync.dto.CandidateProfileDto;
import com.rebellion.skillsync.model.entity.Candidate;
import com.rebellion.skillsync.model.entity.CandidateSkill;
import com.rebellion.skillsync.model.entity.Skill;
import com.rebellion.skillsync.repo.CandidateRepo;
import com.rebellion.skillsync.repo.CandidateSkillRepo;
import com.rebellion.skillsync.repo.SkillRepo;
import com.rebellion.skillsync.repo.UserRepo;
import com.rebellion.skillsync.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final SkillRepo skillRepo;
    private final CandidateSkillRepo candidateSkillRepo;

    private Candidate getCandidateById(Long userId){
        Candidate candidate = candidateRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("No candidate found with userId: " + userId));
        return candidate;
    }

    private HttpStatus deleteResumeByPath(String resumePath) {
        HttpStatus response = null;

        if(!(resumePath == null) && !resumePath.isEmpty()){
            try {
                // locate file
                File file = new File(resumePath);
                // delete file
                file.delete();
                response = HttpStatus.NO_CONTENT;
            } catch (RuntimeException e) {
                response = HttpStatus.BAD_REQUEST;
            }
        } else {
            response = HttpStatus.BAD_REQUEST;
        }

        // return status
        return response;
    }

    @Override
    public CandidateProfileDto getProfile(Long userId) {
        //  find candidate by userId
        Candidate candidate = this.getCandidateById(userId);

        // Get skills from Candidate_Skill
        List<String> skillNames = candidate.getSkills().stream()
                .map(candidateSkill -> candidateSkill.getSkill().getName())
                .toList();

        return CandidateProfileDto.builder()
                .id(candidate.getId())
                .fname(candidate.getUser().getFname())
                .lname(candidate.getUser().getLname())
                .degree(candidate.getDegree())
                .experience(candidate.getExperience())
                .resumePath(candidate.getResumePath())
                .githubUrl(candidate.getGithubUrl())
                .linkedinUrl(candidate.getLinkedinUrl())
                .professionalSummary(candidate.getProfessionalSummary())
                .skills(skillNames)
                .build();
    }

    @Override
    public CandidateProfileDto updateProfile(Long userId, CandidateProfileDto request) {
        //  find candidate by userId
        Candidate candidate = this.getCandidateById(userId);

        //  modify candidate

        // update User fields --> Candidate update cascades to User
        candidate.getUser().setFname(request.getFname());
        candidate.getUser().setLname(request.getLname());

        candidate.setDegree(request.getDegree());
        candidate.setExperience(request.getExperience());
        candidate.setGithubUrl(request.getGithubUrl());
        candidate.setLinkedinUrl(request.getLinkedinUrl());
        candidate.setResumePath(request.getResumePath());
        candidate.setProfessionalSummary(request.getProfessionalSummary());

        // update Skill fields --> Candidate update cascades to Skils
        // Clear existing skills
        candidate.getSkills().clear();  // <== This is causing problem because hibernate has deleted candidate from CandidateSkill yet
        // Leading to addition later before deletion. Deleting manually.
        candidateSkillRepo.deleteByCandidateId(candidate.getId());

        // add skills from incoming list
        if (request.getSkills() != null) {
            // find skill from DB or save skill to DB
            for (String skillName : request.getSkills()) {
                Skill skill = skillRepo.findByNameIgnoreCase(skillName)
                        .orElseGet(() -> skillRepo.save(new Skill(skillName)));

                // create a relation between candidate and skill by entry in candidate_skills
                CandidateSkill candidateSkill = new CandidateSkill();
                candidateSkill.setCandidate(candidate);
                candidateSkill.setSkill(skill);
                candidate.getSkills().add(candidateSkill);
            }
        }

        // save updated candidate
        Candidate updatedCandidate = candidateRepo.save(candidate);

        // map and return CandidateProfileDto
        // return modelMapper.map(updatedCandidate, CandidateProfileDto.class); <== This doesn't work anymore due to mapping issues
        // ModelMapper can’t figure out how to map List<CandidateSkill> to List<String>
        // Going for manual mapping

        List<String> skillNames = candidate.getSkills().stream()
                .map(candidateSkill -> candidateSkill.getSkill().getName())
                .toList();

        return CandidateProfileDto.builder()
                .id(updatedCandidate.getId())
                .fname(updatedCandidate.getUser().getFname())
                .lname(updatedCandidate.getUser().getLname())
                .degree(updatedCandidate.getDegree())
                .experience(updatedCandidate.getExperience())
                .githubUrl(updatedCandidate.getGithubUrl())
                .linkedinUrl(updatedCandidate.getLinkedinUrl())
                .resumePath(updatedCandidate.getResumePath())
                .professionalSummary(updatedCandidate.getProfessionalSummary())
                .skills(skillNames)
                .build();
    }

    @Override
    public String saveResumeToFS(Long userId, MultipartFile file) {

        // find candidate in db
        Candidate candidate = this.getCandidateById(userId);

        try {
            // create directory if it doesn't exist
            String resumeDirectoryPath = "uploads/resumes";
            File resumeDirectory = new File(resumeDirectoryPath);
            if (!resumeDirectory.exists()) {
                resumeDirectory.mkdirs();
            }

            // generate filename
            String fileName = userId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename(); // throws FileNotFoundException if file is null or empty
            String filePath = resumeDirectory.getAbsolutePath() + File.separator + fileName;

            // save file to file system
            file.transferTo(new File(filePath)); // if this works, means has been uploaded. It's safe to delete the previous one.

            // delete the previous one
            this.deleteResumeByPath(candidate.getResumePath());

            //save new filepath in db
            candidate.setResumePath(filePath);
            candidateRepo.save(candidate);

        } catch (IOException e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }

        // return status string
        return "Resume uploaded successfully";
    }

    @Override
    public Resource downloadResumeFromFS(Long userId) {
        Resource response = null;

        // fetch candidate using userId
        Candidate candidate = this.getCandidateById(userId);

        // get file on resumePath
        String resumePath = candidate.getResumePath();
        if(resumePath == null || resumePath.isEmpty()){
            throw new RuntimeException("No resume uploaded.");
        }
        try{
            File resume = new File(resumePath);
            if (!resume.exists()){
                return response;
            }
            // serve file as downloadable
            response = new UrlResource(resume.toURI()); // create a resource using URI that represents resume

        } catch (Exception e) {
            throw new RuntimeException("Download failed: " + e.getMessage());
        }

        return response;
    }

    @Override
    public HttpStatus deleteResumeFromFS(Long userId) {
        // find candidate
        Candidate candidate = this.getCandidateById(userId);
        // find resumePath
        String resumePath = candidate.getResumePath();
        HttpStatus response = this.deleteResumeByPath(resumePath);
        candidateRepo.save(candidate);
        return response;
    }
}
