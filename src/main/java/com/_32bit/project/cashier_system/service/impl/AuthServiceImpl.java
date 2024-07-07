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


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInfoDto.getUsername(),loginInfoDto.getPassword())
        );
        // the program does not reach this line
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        logger.info("User: " + userDetails.getUsername() + ",Has roles: " + userDetails.getAuthorities() + " logged in successfully");
        return new JwtResponseDto(jwt,userDetails.getId(),userDetails.getUsername(),userDetails.getEmail(),roles);

    }

    @Override
    public ResponseEntity<?> registerUser(CreateTeamMemberDto createTeamMemberDto) {
        Boolean existsByUsername = teamMemberRepository.existsByUsername(createTeamMemberDto.getUsername());
        Boolean existsByEmail = teamMemberRepository.existsByEmail(createTeamMemberDto.getEmail());
        Boolean existsByPhoneNumber = teamMemberRepository.existsByPhoneNumber(createTeamMemberDto.getPhoneNumber());
        Boolean salePointExists = salePointRepository.existsByIdAndDeleted(createTeamMemberDto.getSalePointId(),false);
        if (existsByUsername) {
            logger.warn("Username is already taken!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (existsByEmail) {
            logger.warn("Email is already in use!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        if (existsByPhoneNumber) {
            logger.warn("Phone number is already in use!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Phone number is already in use!");
        }

        if (!salePointExists && createTeamMemberDto.getSalePointId() != null){
            logger.warn("Error: SalePoint is not found!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: SalePoint is not found!");
        }

        List<Role> roles = getRoles(createTeamMemberDto);

        if (roles.isEmpty()) {
            logger.warn("Error: Role is not found!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Role is not found!");
        }

        SalePoint salePoint = null;
        if (createTeamMemberDto.getSalePointId() != null) {
            salePoint = salePointRepository.findByIdAndDeleted(createTeamMemberDto.getSalePointId(), false)
                    .orElseThrow(() -> new RuntimeException("Error: SalePoint not found"));
        }
        TeamMember teamMember = TeamMemberMapper.createTeamMemberDtoToTeamMemberDomain(createTeamMemberDto,salePoint,passwordEncoder.encode(createTeamMemberDto.getPassword()));
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

    private List<Role> getRoles(CreateTeamMemberDto createTeamMemberDto) {
        List<String> strRoles = createTeamMemberDto.getRoles();
        List<Role> roles = new ArrayList<>();
        if(createTeamMemberDto.getRoles() != null && !createTeamMemberDto.getRoles().isEmpty()) {
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
        }
        return roles;
    }
}