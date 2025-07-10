package com.rebellion.skillsync.service.impl;

import com.rebellion.skillsync.dto.UserDto;
import com.rebellion.skillsync.dto.UserRegisterDto;
import com.rebellion.skillsync.dto.UserResponseDto;
import com.rebellion.skillsync.model.entity.Admin;
import com.rebellion.skillsync.model.entity.Candidate;
import com.rebellion.skillsync.model.entity.Employer;
import com.rebellion.skillsync.model.entity.User;
import com.rebellion.skillsync.repo.AdminRepo;
import com.rebellion.skillsync.repo.CandidateRepo;
import com.rebellion.skillsync.repo.EmployerRepo;
import com.rebellion.skillsync.repo.UserRepo;
import com.rebellion.skillsync.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final CandidateRepo candidateRepo;
    private final EmployerRepo employerRepo;
    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRegisterDto request) {

        // if same email --> throw exception
        if(userRepo.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists.");
        }

        // map UserRegisterDto to User
        User user = User.builder()
                .fname(request.getFname())
                .lname(request.getLname())
                .email(request.getEmail())
                .dob(request.getDob())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // save User to DB
        userRepo.save(user);

        // create role-based profile
        switch(user.getRole()){
            case CANDIDATE -> candidateRepo.save(Candidate.builder().user(user).build());
            case EMPLOYER -> employerRepo.save(Employer.builder().user(user).build());
            case ADMIN -> adminRepo.save(Admin.builder().user(user).build());
        }

        // map User to UserResponseDto
        UserResponseDto response = UserResponseDto.builder()
                .id(user.getId())
                .fname(user.getFname())
                .lname(user.getLname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        // return UserResponseDto
        return response;
    }

    @Override
    public UserDto getUserById(Long id){
        //  find user in DB
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        //  map user to UserDto and return
        return modelMapper.map(user, UserDto.class);
    }
}
