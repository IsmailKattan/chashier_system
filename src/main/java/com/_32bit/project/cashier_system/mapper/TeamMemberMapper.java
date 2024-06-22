package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.TeamMemberDTO;
import com._32bit.project.cashier_system.domains.TeamMember;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TeamMemberMapper {

    private PasswordEncoder passwordEncoder;
    public TeamMember dtoToDomain(TeamMemberDTO dto) {
        TeamMember teamMember = new TeamMember();

        teamMember.setDeleted(dto.isDeleted());
        teamMember.setInsertionDate(dto.getInsertionDate());
        teamMember.setInsertionTime(dto.getInsertionTime());
        teamMember.setFirstname(dto.getFirstname());
        teamMember.setLastname(dto.getLastname());

        return teamMember;
    }

    public TeamMemberDTO domainToDto(TeamMember teamMember) {
        TeamMemberDTO dto = new TeamMemberDTO(teamMember.getId(),teamMember.getInsertionDate(),teamMember.getInsertionTime());
        dto.setFirstname(teamMember.getFirstname());
        dto.setLastname(teamMember.getLastname());
        dto.setDeleted(teamMember.getDeleted());
        dto.setUsername(dto.getUsername());

        return dto;
    }
}
