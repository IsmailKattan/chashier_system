package com._32bit.project.cashier_system.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import java.sql.Time;
import java.util.Date;
import java.util.Set;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TeamMemberDTO {

    private final long id;
    @Setter
    private String firstname;
    @Setter
    private String lastname;
    @Setter
    private String username;
    @Setter
    private Set<RoleDTO> roles;
    @Setter
    private String phoneNumber;
    @Setter
    private String email;
    @Setter
    private SalePointDTO salePoint;
    @Setter
    private String password;
    private final Date insertionDate;
    private final Time insertionTime;
    @Setter
    private boolean deleted;

}
