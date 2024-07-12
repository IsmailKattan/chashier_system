package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.RoleRepository;
import com._32bit.project.cashier_system.DAO.SalePointRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.request.UpdatePasswordDto;
import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.enums.ERole;
import com._32bit.project.cashier_system.mapper.TeamMemberMapper;
import com._32bit.project.cashier_system.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {

    private final static Logger logger = LogManager.getLogger(TeamMemberServiceImpl.class);
    private final TeamMemberRepository teamMemberRepository;
    private final SalePointRepository salePointRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<?> registerMember(CreateTeamMemberDto createTeamMemberDto) {
        Boolean existsByUsername = teamMemberRepository.existsByUsername(createTeamMemberDto.getUsername());
        Boolean existsByEmail = teamMemberRepository.existsByEmail(createTeamMemberDto.getEmail());
        Boolean existsByPhoneNumber = teamMemberRepository.existsByPhoneNumber(createTeamMemberDto.getPhoneNumber());
        Boolean salePointExists = salePointRepository.existsByIdAndDeleted(createTeamMemberDto.getSalePointId(),false);
        if (
                createTeamMemberDto.getUsername().equalsIgnoreCase("admin") ||
                createTeamMemberDto.getUsername().equalsIgnoreCase("cashier") ||
                createTeamMemberDto.getUsername().equalsIgnoreCase("manager")

        ) {
            logger.warn("Error: Username is already taken!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
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

        List<Role> roles = getRoles(createTeamMemberDto.getRoles());

        if (roles.isEmpty()) {
            logger.warn("Error: Role is not found!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Role is not found!");
        }

        Optional<SalePoint> salePointOptional = salePointRepository.findById(createTeamMemberDto.getSalePointId());
        if (salePointOptional.isEmpty()){
            logger.warn("Error: SalePoint is not found!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: SalePoint is not found!");
        }
        TeamMember teamMember = TeamMemberMapper.createTeamMemberDtoToTeamMemberDomain(createTeamMemberDto,salePointOptional.get(),passwordEncoder.encode(createTeamMemberDto.getPassword()));
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

    @Override
    public ResponseEntity<?> getMemberByIdAndDeleted(Long id, Boolean deleted) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByIdAndDeleted(id,deleted);
        if (teamMemberOptional.isEmpty()){
            logger.warn("Error: TeamMember not found!");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: TeamMember not found!");
        }
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("TeamMember found successfully"),
                        TeamMemberMapper.toTeamMemberInfoDto(teamMemberOptional.get())
                )
        );
    }

    @Override
    public ResponseEntity<?> getMemberByUsernameAndDeleted(String username, Boolean deleted) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByUsernameAndDeleted(username,deleted);
        if (teamMemberOptional.isEmpty()){
            logger.warn("Error: TeamMember not found!");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: TeamMember not found!");
        }
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("TeamMember found successfully"),
                        TeamMemberMapper.toTeamMemberInfoDto(teamMemberOptional.get())
                )
        );
    }

    @Override
    public ResponseEntity<?> getAllMembersByDeleted(Boolean deleted) {
        List<TeamMember> teamMembers = teamMemberRepository.findAllByDeleted(deleted);
        if (teamMembers.isEmpty()){
            logger.warn("Error: TeamMembers not found!");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: TeamMembers not found!");
        }
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("TeamMembers found successfully"),
                        teamMembers.stream()
                                .map(TeamMemberMapper::toTeamMemberInfoDto)
                                .toList()
                )
        );
    }
    private List<Role> getRoles(List<String> roleNames){
        List<Role> roles = new ArrayList<>();
        if(roleNames != null && !roleNames.isEmpty()) {
            for (String strRole:roleNames) {
                if(ERole.contains("ROLE_" + strRole.toUpperCase())) {
                    Role role = roleRepository.findByNameAndDeleted(ERole.valueOf("ROLE_" + strRole.toUpperCase()),false)
                            .orElseThrow(()->new RuntimeException("Error: role " + strRole + " not found"));
                    roles.add(role);
                }
            }
        }
        return roles;
    }

    @Override
    public ResponseEntity<?> updateMember(Long id, CreateTeamMemberDto createTeamMemberDto) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByUsernameAndDeleted(createTeamMemberDto.getUsername(),false);
        if (teamMemberOptional.isEmpty()){
            logger.warn("Error: TeamMember not found!");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: TeamMember not found!");
        }
        Boolean existsByUsername = teamMemberRepository.existsByUsername(createTeamMemberDto.getUsername());
        Boolean existsByEmail = teamMemberRepository.existsByEmail(createTeamMemberDto.getEmail());
        Boolean existsByPhoneNumber = teamMemberRepository.existsByPhoneNumber(createTeamMemberDto.getPhoneNumber());
        Boolean salePointExists = salePointRepository.existsByIdAndDeleted(createTeamMemberDto.getSalePointId(),false);
        TeamMember teamMember = teamMemberOptional.get();
        if (!Objects.equals(createTeamMemberDto.getUsername(), teamMember.getUsername()) && existsByUsername) {
            logger.warn("Username is already taken!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        if (!Objects.equals(createTeamMemberDto.getEmail(), teamMember.getEmail()) && existsByEmail) {
            logger.warn("Email is already in use!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }
        if (!Objects.equals(createTeamMemberDto.getPhoneNumber(), teamMember.getPhoneNumber()) && existsByPhoneNumber) {
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
        List<Role> roles = getRoles(createTeamMemberDto.getRoles());

        if (roles.isEmpty()) {
            logger.warn("Error: Role is not found!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Role is not found!");
        }
        Optional<SalePoint> salePointOptional = salePointRepository.findById(createTeamMemberDto.getSalePointId());
        if (salePointOptional.isEmpty()){
            logger.warn("Error: SalePoint is not found!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: SalePoint is not found!");
        }
        teamMember.setFirstname(createTeamMemberDto.getFirstname());
        teamMember.setLastname(createTeamMemberDto.getLastname());
        teamMember.setUsername(createTeamMemberDto.getUsername());
        teamMember.setEmail(createTeamMemberDto.getEmail());
        teamMember.setPhoneNumber(createTeamMemberDto.getPhoneNumber());
        if (!teamMember.getUsername().equals("admin")) {
            teamMember.setSalePoint(salePointOptional.get());
        }
        teamMember.setRoles(roles);
        teamMemberRepository.save(teamMember);
        logger.info("User: " + teamMember.getUsername() + " updated successfully");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("User updated successfully"),
                        TeamMemberMapper.toTeamMemberInfoDto(teamMember)
                )
        );

    }

    @Override
    public ResponseEntity<?> deleteMember(Long id) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByIdAndDeleted(id,false);
        if (teamMemberOptional.isEmpty()){
            logger.warn("Error: TeamMember not found!");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: TeamMember not found!");
        }
        TeamMember teamMember = teamMemberOptional.get();
        teamMember.setDeleted(true);
        teamMemberRepository.save(teamMember);
        logger.info("User: " + teamMember.getUsername() + " deleted successfully");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("User deleted successfully"),
                        TeamMemberMapper.toTeamMemberInfoDto(teamMember)
                )
        );
    }

    @Override
    public ResponseEntity<?> restoreMember(Long id) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByIdAndDeleted(id,true);
        if (teamMemberOptional.isEmpty()){
            logger.warn("Error: TeamMember not found!");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: TeamMember not found!");
        }
        TeamMember teamMember = teamMemberOptional.get();
        teamMember.setDeleted(false);
        teamMemberRepository.save(teamMember);
        logger.info("User: " + teamMember.getUsername() + " restored successfully");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("User restored successfully"),
                        TeamMemberMapper.toTeamMemberInfoDto(teamMember)
                )
        );
    }

    @Override
    public ResponseEntity<?> updateAuthority(Long id, List<String> authority) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByIdAndDeleted(id,false);
        if (teamMemberOptional.isEmpty()){
            logger.warn("Error: TeamMember not found!");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: TeamMember not found!");
        }
        TeamMember teamMember = teamMemberOptional.get();
        List<Role> roles = getRoles(authority);
        if (roles.isEmpty()) {
            logger.warn("Error: Role is not found!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Role is not found!");
        }
        teamMember.setRoles(roles);
        teamMemberRepository.save(teamMember);
        logger.info("User: " + teamMember.getUsername() + " updated successfully");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("User updated successfully"),
                        TeamMemberMapper.toTeamMemberInfoDto(teamMember)
                )
        );
    }

    @Override
    public ResponseEntity<?> updatePassword(Long id, UpdatePasswordDto password) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByIdAndDeleted(id,false);
        if (teamMemberOptional.isEmpty()){
            logger.warn("Error: TeamMember not found!");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: TeamMember not found!");
        }
        TeamMember teamMember = teamMemberOptional.get();
        teamMember.setPassword(passwordEncoder.encode(password.getPassword()));
        teamMemberRepository.save(teamMember);
        logger.info("User: " + teamMember.getUsername() + " updated successfully");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("User updated successfully"),
                        TeamMemberMapper.toTeamMemberInfoDto(teamMember)
                )
        );
    }

    @Override
    public ResponseEntity<?> updateSalePoint(Long id, Long salePointId) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByIdAndDeleted(id,false);
        if (teamMemberOptional.isEmpty()){
            logger.warn("Error: TeamMember not found!");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: TeamMember not found!");
        }
        Optional<SalePoint> salePointOptional = salePointRepository.findById(salePointId);
        if (salePointOptional.isEmpty()){
            logger.warn("Error: SalePoint not found!");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: SalePoint not found!");
        }
        TeamMember teamMember = teamMemberOptional.get();
        if (teamMember.getUsername().equals("admin")) {
            logger.warn("Error: You are not authorized to change this user's sale point!");
            return ResponseEntity
                    .badRequest()
                    .body("Error: You are not authorized to change this user's sale point!");
        }
        teamMember.setSalePoint(salePointOptional.get());
        teamMemberRepository.save(teamMember);
        logger.info("User: " + teamMember.getUsername() + " updated successfully");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("User updated successfully"),
                        TeamMemberMapper.toTeamMemberInfoDto(teamMember)
                )
        );
    }
}
