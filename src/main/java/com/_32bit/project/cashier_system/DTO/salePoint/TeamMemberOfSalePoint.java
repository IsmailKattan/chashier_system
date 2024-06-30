package com._32bit.project.cashier_system.DTO.salePoint;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TeamMemberOfSalePoint {
    private Long id;
    private String username;
    private String name;
}
