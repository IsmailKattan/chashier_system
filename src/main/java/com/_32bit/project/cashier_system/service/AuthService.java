package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.JwtResponseDto;
import com._32bit.project.cashier_system.DTO.teamMember.request.LoginInfoDto;

public interface AuthService {

    JwtResponseDto authenticateUser(LoginInfoDto loginInfoDto);

    Object registerUser(CreateTeamMemberDto createTeamMemberDto);
}
