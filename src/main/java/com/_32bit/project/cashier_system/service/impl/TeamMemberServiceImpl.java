package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DAO.UserCredentialRepository;
import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.TeamMemberInfoDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.UserCredentialInfoDto;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.UserCredential;
import com._32bit.project.cashier_system.mapper.TeamMemberMapper;
import com._32bit.project.cashier_system.mapper.UserCredentialMapper;
import com._32bit.project.cashier_system.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository, UserCredentialRepository userCredentialRepository,PasswordEncoder passwordEncoder) {
        this.teamMemberRepository = teamMemberRepository;
        this.userCredentialRepository = userCredentialRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TeamMemberInfoDto getTeamMemberInfo(String username) {
        Optional<UserCredential> userCredential = userCredentialRepository.findByUsername(username);
        if(userCredential.isPresent()){
            Optional<TeamMember> teamMember = teamMemberRepository.findByUserCredentialAndDeleted(userCredential.get(),false);
            if (teamMember.isPresent()) {
                return TeamMemberMapper.toTeamMemberInfoDto(teamMember.get());
            }
        }

        return null;
    }

    @Override
    public List<TeamMemberInfoDto> getTeamMemberInfoByName(String name) {
        Optional<List<TeamMember>> teamMembers = teamMemberRepository.findByFirstnameOrLastnameAndDeleted(name,name,false);
        if(teamMembers.isPresent()) {
            List<TeamMemberInfoDto> listOfDto = new ArrayList<>();
            for (TeamMember teamMember : teamMembers.get()) {
                listOfDto.add(TeamMemberMapper.toTeamMemberInfoDto((teamMember)));
            }
            return listOfDto;
        }
        return null;
    }

    @Override
    public UserCredentialInfoDto getMemberCredential(String username) {
        Optional<UserCredential> userCredential = userCredentialRepository.findByUsername(username);
        if(userCredential.isPresent()) {
            return UserCredentialMapper.toUserCredentialInfoDto(userCredential.get());
        }

        return null;
    }

    @Override
    public String deleteMember(String username) {
        Optional<UserCredential> userCredential = userCredentialRepository.findByUsername(username);
        if(userCredential.isPresent()) {
            Optional<TeamMember> teamMember = teamMemberRepository.findByUserCredentialAndDeleted(userCredential.get(),false);
            if(teamMember.isPresent()) {
                teamMember.get().setDeleted(true);
                userCredential.get().setDeleted(true);
                teamMemberRepository.save(teamMember.get());
                userCredentialRepository.save(userCredential.get());

                return "Member With Username: " +
                        userCredential.get().getUsername() +
                        ", Name and surname: " +
                        teamMember.get().getFirstname()+" " +teamMember.get().getLastname() +
                        ", has been DELETED";
            }
            userCredential.get().setDeleted(true);
            userCredentialRepository.save(userCredential.get());

            return "Member With Username:  " +
                    userCredential.get().getUsername() +
                    ", Is Not A Member";
        }
        return "User Not Found";
    }

    @Override
    public String UnDeleteMember(String username) {
        Optional<UserCredential> userCredential = userCredentialRepository.findByUsername(username);
        if(userCredential.isPresent()) {
            Optional<TeamMember> teamMember = teamMemberRepository.findByUserCredentialAndDeleted(userCredential.get(),true);
            if(teamMember.isPresent()) {
                teamMember.get().setDeleted(false);
                userCredential.get().setDeleted(false);
                teamMemberRepository.save(teamMember.get());
                userCredentialRepository.save(userCredential.get());

                return "Member With Username: " +
                        userCredential.get().getUsername() +
                        ", Name and surname: " +
                        teamMember.get().getFirstname()+" " +teamMember.get().getLastname() +
                        ", has been UNDELETED";
            }
            userCredential.get().setDeleted(false);
            userCredentialRepository.save(userCredential.get());

            return "Member With Username:  " +
                    userCredential.get().getUsername() +
                    ", Is Not A Member";
        }
        return "User Not Found";
    }

    @Override
    public TeamMemberInfoDto createTeamMember(CreateTeamMemberDto createTeamMemberDto) {
        TeamMember teamMember =  TeamMemberMapper.createTeamMemberDtoToTeamMemberDomain(createTeamMemberDto);
        UserCredential userCredential = UserCredentialMapper.createTeamMemberDtoToDomain(createTeamMemberDto,passwordEncoder.encode(createTeamMemberDto.getPassword()));
        userCredential.setTeamMember(teamMember);
        teamMember.setUserCredential(userCredential);
        teamMemberRepository.save(teamMember);
        userCredentialRepository.save(userCredential);

        return TeamMemberMapper.toTeamMemberInfoDto(teamMember);
    }

    @Override
    public TeamMemberInfoDto updateResource(String username, CreateTeamMemberDto createTeamMemberDto) {
        Optional<UserCredential> userCredential = userCredentialRepository.findByUsername(username);
        TeamMember teamMember;
        if (userCredential.isPresent()) {
            teamMember = userCredential.get().getTeamMember();
            TeamMemberMapper.updateTeamMember(teamMember,createTeamMemberDto);
            UserCredentialMapper.updateUserCredential(userCredential.get(), createTeamMemberDto);

            teamMemberRepository.save(teamMember);
            userCredentialRepository.save(userCredential.get());

            return TeamMemberMapper.toTeamMemberInfoDto(teamMember);
        }

        return null;
    }
}
