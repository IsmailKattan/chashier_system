package com._32bit.project.cashier_system.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private final long id;
    private final Date saleDate;
    private final Time saleTime;
    @Setter
    private String paymentType;
    @Setter
    private double total;
    @Setter
    private double receivedMoney;
    @Setter
    private double change;
    @Setter
    private boolean isDiscounted;
    @Setter
    private double discountRate;
    @Setter
    private double discountedTotal;
    private final TeamMemberDTO soldBy;
    private final SessionDTO session;
    @Setter
    private boolean deleted;
}
