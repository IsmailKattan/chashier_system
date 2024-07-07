package com._32bit.project.cashier_system.DTO.teamMember.response;


import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberInfoDto {
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate insertionDate;
    private LocalTime insertionTime;
    private List<String> roles;
    private String salePointName;
    private String salePointAddress;
}
