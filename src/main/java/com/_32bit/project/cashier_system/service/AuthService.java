package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.JwtResponseDto;
import com._32bit.project.cashier_system.DTO.teamMember.request.LoginInfoDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    JwtResponseDto authenticateUser(LoginInfoDto loginInfoDto);

    ResponseEntity<?> registerUser(CreateTeamMemberDto createTeamMemberDto);
}
