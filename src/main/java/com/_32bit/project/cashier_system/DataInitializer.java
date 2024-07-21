package com._32bit.project.cashier_system;

import com._32bit.project.cashier_system.DAO.RoleRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.enums.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TeamMemberRepository teamMemberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByNameAndDeleted(ERole.ROLE_ADMIN, false).isEmpty()) {
            roleRepository.save(new Role(null, ERole.ROLE_ADMIN, false));
        }
        if (roleRepository.findByNameAndDeleted(ERole.ROLE_MANAGER, false).isEmpty()) {
            roleRepository.save(new Role(null, ERole.ROLE_MANAGER, false));
        }
        if (roleRepository.findByNameAndDeleted(ERole.ROLE_CASHIER, false).isEmpty()) {
            roleRepository.save(new Role(null, ERole.ROLE_CASHIER, false));
        }

        if (teamMemberRepository.findByUsernameAndDeleted("admin", false).isEmpty()) {
            List<Role> roles = roleRepository.findAll(); // Fetch roles to attach to team member
            TeamMember teamMember = TeamMember.builder()
                    .firstname("admin")
                    .lastname("admin")
                    .username("admin")
                    .password(passwordEncoder.encode("32-bit"))
                    .email("admin@gmail.com")
                    .phoneNumber("0123456789")
                    .roles(roles) // Attach fetched roles
                    .build();
            teamMemberRepository.save(teamMember);
        }
    }
}
