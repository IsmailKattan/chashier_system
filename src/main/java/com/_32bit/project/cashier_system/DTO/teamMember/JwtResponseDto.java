package com._32bit.project.cashier_system.DTO.teamMember;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@Builder
public class JwtResponseDto {

    private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;


    public JwtResponseDto(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
