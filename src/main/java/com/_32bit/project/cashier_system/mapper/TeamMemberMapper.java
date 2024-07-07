package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.TeamMemberInfoDto;
import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamMemberMapper {

    public static TeamMember createTeamMemberDtoToTeamMemberDomain(CreateTeamMemberDto createTeamMemberDto, SalePoint salePoint, String password) {

        return TeamMember.builder()
                .firstname(createTeamMemberDto.getFirstname())
                .username(createTeamMemberDto.getUsername())
                .phoneNumber(createTeamMemberDto.getPhoneNumber())
                .lastname(createTeamMemberDto.getLastname())
                .email(createTeamMemberDto.getEmail())
                .password(password)
                .insertionDate(LocalDate.now())
                .insertionTime(LocalTime.now())
                .salePoint(salePoint)
                .deleted(false)
                .build();
    }

    public static TeamMemberInfoDto toTeamMemberInfoDto(TeamMember teamMember) {
        return TeamMemberInfoDto.builder()
                .id(teamMember.getId())
                .firstname(teamMember.getFirstname())
                .lastname(teamMember.getLastname())
                .insertionDate(teamMember.getInsertionDate())
                .insertionTime(teamMember.getInsertionTime())
                .roles(teamMember.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toList())
                )
                .salePointName(teamMember.getSalePoint() != null ? teamMember.getSalePoint().getName() : null)
                .salePointAddress(teamMember.getSalePoint() != null ? teamMember.getSalePoint().getAddress() : null)
                .build();
    }
}
