package com._32bit.project.cashier_system.DTO;


import com._32bit.project.cashier_system.domains.SalePoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class SessionDTO {
    private final long id;
    private final Date openingDate;
    private final Time openingtime;
    @Setter
    private Date closingDate;
    @Setter
    private Time closingTime;
    private final double openingCash;
    @Setter
    private double closingCash;
    @Setter
    private double balance;
    private final TeamMemberDTO openedBy;
    private final SalePointDTO salePoint;
    @Setter
    private boolean deleted;

}
