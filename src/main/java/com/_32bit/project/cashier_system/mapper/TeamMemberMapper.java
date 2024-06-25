package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.TeamMemberDTO;
import com._32bit.project.cashier_system.DTO.teamMember.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.TeamMemberInfoDto;
import com._32bit.project.cashier_system.domains.TeamMember;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TeamMemberMapper {

    private PasswordEncoder passwordEncoder;

    public static TeamMember createTeamMemberDtoToTeamMemberDomain(CreateTeamMemberDto createTeamMemberDto) {

        return TeamMember.builder()
                .firstname(createTeamMemberDto.getFirstname())
                .lastname(createTeamMemberDto.getLastname())
                .insertionDate(LocalDate.now())
                .insertionTime(LocalTime.now())
                .deleted(false)
                .build();
    }

    public static TeamMember updateTeamMember(TeamMember teamMember, CreateTeamMemberDto createTeamMemberDto) {
        teamMember.setFirstname(createTeamMemberDto.getFirstname());
        teamMember.setLastname(createTeamMemberDto.getLastname());
        return teamMember;
    }

    public static TeamMemberInfoDto toTeamMemberInfoDto(TeamMember teamMember) {
        return TeamMemberInfoDto.builder()
                .id(teamMember.getId())
                .firstname(teamMember.getFirstname())
                .lastname(teamMember.getLastname())
                .insertionDate(teamMember.getInsertionDate())
                .insertionTime(teamMember.getInsertionTime())
                .roles(teamMember.getUserCredential().getRoles().stream()
                        .map(role -> role.getName().name())  // Convert ERole to String
                        .collect(Collectors.toList()))
                .build();
    }
}
