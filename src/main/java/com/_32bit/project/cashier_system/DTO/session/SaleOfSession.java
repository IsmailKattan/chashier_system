package com._32bit.project.cashier_system.DTO.session;

import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleOfSession {
    private long id;
    private LocalDate saleDate;
    private LocalTime saleTime;
    private double saleAmount;
}
