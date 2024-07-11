package com._32bit.project.cashier_system.DTO.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionInfoResponse {

    private long id;
    private long salePointId;
    private String salePointName;
    private LocalDate openingDate;
    private LocalTime openingTime;
    private LocalDate closingDate;
    private LocalTime closingTime;
    private boolean closed;
    private String openedBy;
    private String closedBy;
    List<SaleOfSession> sales;
}
