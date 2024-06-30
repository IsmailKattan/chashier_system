package com._32bit.project.cashier_system.DTO.teamMember.response;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialInfoDto {

    private String member;
    private String username;
    private String email;
    private String phoneNumber;
}
