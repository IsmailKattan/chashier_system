package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DTO.TeamMemberDTO;
import com._32bit.project.cashier_system.domains.AuthenticationResponse;
import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.TeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TeamMemberRepository teamMemberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    /**
     public AuthenticationResponse register(TeamMemberDTO request) {
        TeamMember teamMember = new TeamMember();
        teamMember.setFirstname(request.getFirstname());
        teamMember.setLastname(request.getLastname());
        teamMember.setUsername(request.getUsername());
        teamMember.setPassword(passwordEncoder.encode(request.getPassword()));
        teamMember.setRoles(request.getRoles());
        teamMember.setPhoneNumber(request.getPhoneNumber());
        teamMember.setEmail(request.getEmail());
        teamMember.setInsertionDate(new Date(System.currentTimeMillis()));
        teamMember.setInsertionTime(new Time(System.currentTimeMillis()));
        teamMember.setSalePoint(request.getSalePoint());

        teamMember = teamMemberRepository.save(teamMember);

        String token = jwtService.generateToken(teamMember);

        return new AuthenticationResponse(token);
    }*/

    public AuthenticationResponse authenticate(TeamMemberDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        TeamMember teamMember = teamMemberRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(teamMember);

        return new AuthenticationResponse(token);
    }
}
