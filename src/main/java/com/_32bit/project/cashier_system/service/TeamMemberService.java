package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.TeamMemberInfoDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.UserCredentialInfoDto;

import java.util.List;

public interface TeamMemberService {


    TeamMemberInfoDto getTeamMemberInfo(String username);

    List<TeamMemberInfoDto> getTeamMemberInfoByName(String name);

    UserCredentialInfoDto getMemberCredential(String username);

    String deleteMember(String username);

    String UnDeleteMember(String username);

    TeamMemberInfoDto createTeamMember(CreateTeamMemberDto createTeamMemberDto);

    TeamMemberInfoDto updateResource(String username, CreateTeamMemberDto createTeamMemberDto);
}
