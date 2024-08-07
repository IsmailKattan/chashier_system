package com._32bit.project.cashier_system.DTO.teamMember.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class LoginInfoDto {
    private String username;
    private String password;
}
