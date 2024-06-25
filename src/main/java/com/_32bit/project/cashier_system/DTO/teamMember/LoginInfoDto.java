package com._32bit.project.cashier_system.DTO.teamMember;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class LoginInfoDto {
    private String username;
    private String password;
}
