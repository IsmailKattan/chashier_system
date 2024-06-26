package com._32bit.project.cashier_system.DTO.teamMember;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class LoginInfoDto {
    private String username;
    private String password;
}
