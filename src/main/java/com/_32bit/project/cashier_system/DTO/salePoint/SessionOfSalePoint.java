package com._32bit.project.cashier_system.DTO.salePoint;

import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class SessionOfSalePoint {
    private Long id;
    private LocalDate openingDate;
    private LocalTime openingTime;
    private LocalDate closingDate;
    private LocalTime closingTime;
}
