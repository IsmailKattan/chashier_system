package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.teamMember.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.JwtResponseDto;
import com._32bit.project.cashier_system.DTO.teamMember.LoginInfoDto;

public interface AuthService {

    JwtResponseDto authenticateUser(LoginInfoDto loginInfoDto);

    Object registerUser(CreateTeamMemberDto createTeamMemberDto);
}
