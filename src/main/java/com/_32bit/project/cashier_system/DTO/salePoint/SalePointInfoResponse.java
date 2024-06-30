package com._32bit.project.cashier_system.DTO.salePoint;

import com._32bit.project.cashier_system.DTO.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class SalePointInfoResponse {

    private Long id;
    private String name;
    private Date createdAtDate;
    private Time createdAtTime;
    private String address;
    private String createdBy;


    private List<SessionOfSalePoint> sessions;
    private List<TeamMemberOfSalePoint> teamMembers;

}

