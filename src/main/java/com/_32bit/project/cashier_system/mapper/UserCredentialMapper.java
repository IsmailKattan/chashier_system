package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.UserCredentialInfoDto;
import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.UserCredential;
import com._32bit.project.cashier_system.domains.enums.ERole;

import java.util.List;

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
        List<Role> roles = createTeamMemberDto.getRoles().stream()
                .map(role -> Role.builder().name(ERole.valueOf(role)).build())
                .toList();

        userCredential.setEmail(createTeamMemberDto.getEmail());
        userCredential.setPhoneNumber(createTeamMemberDto.getPhoneNumber());
        userCredential.setUsername(createTeamMemberDto.getUsername());
        userCredential.setRoles(roles);
        return userCredential;
    }

}
