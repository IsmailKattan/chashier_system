package com._32bit.project.cashier_system.DTO.teamMember;


import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialInfoDto {

    private String member;
    private String username;
    private String email;
    private String phoneNumber;
}
