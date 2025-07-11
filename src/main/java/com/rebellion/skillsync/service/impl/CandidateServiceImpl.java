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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final SkillRepo skillRepo;
    private final CandidateSkillRepo candidateSkillRepo;

    @Override
    public CandidateProfileDto getProfile(Long userId) {
        //  find candidate by userId
        Candidate candidate = candidateRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("No candidate with userId: " + userId));

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
        Candidate candidate = candidateRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("No candidate with userId: " + userId));

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
}
