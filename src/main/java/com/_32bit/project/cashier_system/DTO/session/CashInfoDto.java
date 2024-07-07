package com._32bit.project.cashier_system.DTO.session;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashInfoDto {
    private Long salePointId;
    private double cashInfo;
}
