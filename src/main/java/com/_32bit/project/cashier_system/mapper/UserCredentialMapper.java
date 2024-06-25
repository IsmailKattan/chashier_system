package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.teamMember.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.UserCredentialInfoDto;
import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.UserCredential;
import com._32bit.project.cashier_system.domains.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserCredentialMapper {


    public static UserCredentialInfoDto toUserCredentialInfoDto(UserCredential userCredential) {
        return UserCredentialInfoDto.builder()
                .email(userCredential.getEmail())
                .member((userCredential.getTeamMember().getFirstname()).concat(userCredential.getTeamMember().getFirstname()))
                .phoneNumber(userCredential.getPhoneNumber())
                .username(userCredential.getUsername())
                .build();
    }

    public static UserCredential createTeamMemberDtoToDomain(CreateTeamMemberDto createTeamMemberDto,String password) {

        return UserCredential.builder()
                .username(createTeamMemberDto.getUsername())
                .password(password)
                .email(createTeamMemberDto.getEmail())
                .phoneNumber(createTeamMemberDto.getPhoneNumber())
                .deleted(false)
                .build();
    }

    public static UserCredential updateUserCredential(UserCredential userCredential, CreateTeamMemberDto createTeamMemberDto) {

        List<Role> roles = new ArrayList<>();
        for (String role: createTeamMemberDto.getRoles()) {
            roles.add(Role.builder()
                            .name(ERole.valueOf(role))
                    .build());
        }
        userCredential.setEmail(createTeamMemberDto.getEmail());
        userCredential.setPhoneNumber(createTeamMemberDto.getPhoneNumber());
        userCredential.setUsername(createTeamMemberDto.getUsername());
        userCredential.setRoles(roles);
        return userCredential;
    }
}
