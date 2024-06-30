package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.TeamMemberInfoDto;
import com._32bit.project.cashier_system.domains.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TeamMemberMapper {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public TeamMemberMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static TeamMember createTeamMemberDtoToTeamMemberDomain(CreateTeamMemberDto createTeamMemberDto) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateWithoutTime = calendar.getTime();

        return TeamMember.builder()
                .firstname(createTeamMemberDto.getFirstname())
                .lastname(createTeamMemberDto.getLastname())
                .insertionDate(dateWithoutTime)
                .insertionTime(new Time(System.currentTimeMillis()))
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
