package com._32bit.project.cashier_system.DTO.salePoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@Data
@Builder
public class SessionOfSalePoint {
    private Long id;
    private Date openingDate;
    private Time openingTime;
    private Date closingDate;
    private Time closingTime;
}
