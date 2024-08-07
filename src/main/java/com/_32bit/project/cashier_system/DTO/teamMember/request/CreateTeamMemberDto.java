package com._32bit.project.cashier_system.DTO.teamMember.request;


import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateTeamMemberDto {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private List<String> roles;
    private Long salePointId;

}
