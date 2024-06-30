package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.RoleRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DAO.UserCredentialRepository;
import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.JwtResponseDto;
import com._32bit.project.cashier_system.DTO.teamMember.request.LoginInfoDto;
import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.UserCredential;
import com._32bit.project.cashier_system.domains.enums.ERole;
import com._32bit.project.cashier_system.mapper.TeamMemberMapper;
import com._32bit.project.cashier_system.mapper.UserCredentialMapper;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.security.service.UserDetailsImpl;
import com._32bit.project.cashier_system.service.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserCredentialRepository userCredentialRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, RoleRepository roleRepository, UserCredentialRepository userCredentialRepository, TeamMemberRepository teamMemberRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.userCredentialRepository = userCredentialRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtResponseDto authenticateUser(LoginInfoDto loginInfoDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInfoDto.getUsername(),loginInfoDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        logger.info("User: " + userDetails.getUsername() + ",Has roles: " + userDetails.getAuthorities() + " logged in successfully");
        return new JwtResponseDto(jwt,userDetails.getId(),userDetails.getUsername(),userDetails.getEmail(),roles);

    }

    @Override
    public Object registerUser(CreateTeamMemberDto createTeamMemberDto) {

        List<String> strRoles = createTeamMemberDto.getRoles();
        List<Role> roles = new ArrayList<>();
        if(createTeamMemberDto.getRoles()!=null && !createTeamMemberDto.getRoles().isEmpty()) {
            strRoles.forEach(role->{
                switch (role) {
                    case "admin" :
                        Role adminRole = roleRepository.findByNameAndDeleted(ERole.ROLE_ADMIN,false)
                                .orElseThrow(()->new RuntimeException("Error: role ADMIN not found"));
                        roles.add(adminRole);

                        break;
                    case "manager":
                        Role managerRole = roleRepository.findByNameAndDeleted(ERole.ROLE_MANAGER,false)
                                .orElseThrow(()->new RuntimeException("Error: role Manager not found"));
                        roles.add(managerRole);

                        break;
                    case "cashier":
                        Role cashierRole = roleRepository.findByNameAndDeleted(ERole.ROLE_CASHIER,false)
                                .orElseThrow(() -> new RuntimeException("Error: role CASHIER not found"));
                        roles.add(cashierRole);

                        break;
                }
            });
        } else if (createTeamMemberDto.getRoles().isEmpty()) {
            throw new RuntimeException("Error: user has no role!");
        }

        if (roles.isEmpty()) {
            throw new RuntimeException("Error: unable role/roles");
        }

        TeamMember teamMember = TeamMemberMapper.createTeamMemberDtoToTeamMemberDomain(createTeamMemberDto);
        UserCredential userCredential = UserCredentialMapper.createTeamMemberDtoToDomain(createTeamMemberDto,passwordEncoder.encode(createTeamMemberDto.getPassword()));
        userCredential.setRoles(roles);

        userCredentialRepository.save(userCredential);
        teamMember.setUserCredential(userCredential);
        teamMemberRepository.save(teamMember);
        logger.info("User: " + userCredential.getUsername() +
                ", Has Roles: "+userCredential.getRoles().stream().map(Role::getName).toList()
                + " registered successfully");

        return TeamMemberMapper.toTeamMemberInfoDto(teamMember);
    }
}