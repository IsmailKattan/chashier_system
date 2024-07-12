package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.RoleRepository;
import com._32bit.project.cashier_system.DAO.SalePointRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.JwtResponseDto;
import com._32bit.project.cashier_system.DTO.teamMember.request.LoginInfoDto;
import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.enums.ERole;
import com._32bit.project.cashier_system.mapper.TeamMemberMapper;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.security.service.UserDetailsImpl;
import com._32bit.project.cashier_system.service.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class AuthServiceImpl implements AuthService {

    private final static Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SalePointRepository salePointRepository;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, RoleRepository roleRepository, TeamMemberRepository teamMemberRepository, PasswordEncoder passwordEncoder, SalePointRepository salePointRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.passwordEncoder = passwordEncoder;
        this.salePointRepository = salePointRepository;
    }

    @Override
    public JwtResponseDto authenticateUser(LoginInfoDto loginInfoDto) {
        logger.info("Attempting authentication for user: " + loginInfoDto.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginInfoDto.getUsername(), loginInfoDto.getPassword())
            );
            logger.info("Authentication successful for user: " + loginInfoDto.getUsername());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Set authentication in SecurityContext");

            String jwt = jwtUtils.generateJwtToken(authentication);
            logger.info("JWT token generated successfully");

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

            logger.info("User: " + userDetails.getUsername() + " has roles: " + roles);
            return new JwtResponseDto(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
        } catch (Exception e) {
            logger.error("Authentication failed for user: " + loginInfoDto.getUsername(), e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<?> registerUser(CreateTeamMemberDto createTeamMemberDto) {

        if(createTeamMemberDto.getUsername().equals("admin")){
            Boolean existsByUsername = teamMemberRepository.existsByUsername(createTeamMemberDto.getUsername());
            if (existsByUsername) {
                logger.warn("Username is already taken!");
                return ResponseEntity
                        .badRequest()
                        .body("Error: Username is already taken!");
            }

            List<Role> roles = getRoles(createTeamMemberDto);

            if (roles.isEmpty()) {
                logger.warn("Error: Role is not found!");
                return ResponseEntity
                        .badRequest()
                        .body("Error: Role is not found!");
            }

            TeamMember teamMember = new TeamMember();
            teamMember.setUsername(createTeamMemberDto.getUsername());
            teamMember.setEmail(createTeamMemberDto.getEmail());
            teamMember.setPassword(passwordEncoder.encode(createTeamMemberDto.getPassword()));
            teamMember.setPhoneNumber(createTeamMemberDto.getPhoneNumber());
            teamMember.setFirstname(createTeamMemberDto.getFirstname());
            teamMember.setLastname(createTeamMemberDto.getLastname());
            teamMember.setRoles(roles);

            teamMemberRepository.save(teamMember);
            logger.info("Inserted in: " + teamMember.getInsertionDate() + " " + teamMember.getInsertionTime()) ;
            logger.info("User: " + teamMember.getUsername() + " registered successfully");
            return ResponseEntity.ok(
                    new ObjectWithMessageResponse(
                            new MessageResponse("User registered successfully"),
                            TeamMemberMapper.toTeamMemberInfoDto(teamMember)
                    )
            );
        }
        else return ResponseEntity.badRequest().body("Error: You are not authorized to create this user");
    }

    private List<Role> getRoles(CreateTeamMemberDto createTeamMemberDto) {
        List<String> strRoles = createTeamMemberDto.getRoles();
        List<Role> roles = new ArrayList<>();
        if(createTeamMemberDto.getRoles() != null && !createTeamMemberDto.getRoles().isEmpty()) {
            for (String strRole:strRoles) {
                if(ERole.contains("ROLE_" + strRole.toUpperCase())) {
                    Role role = roleRepository.findByNameAndDeleted(ERole.valueOf("ROLE_" + strRole.toUpperCase()),false)
                            .orElseThrow(()->new RuntimeException("Error: role " + strRole + " not found"));
                    roles.add(role);
                }
            }
        }
        return roles;
    }
}