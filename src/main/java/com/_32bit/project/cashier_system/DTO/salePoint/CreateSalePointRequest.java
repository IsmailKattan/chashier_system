package com._32bit.project.cashier_system.DTO.salePoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateSalePointRequest {
    @JsonIgnore
    private Long id;
    private String name;
    private String address;
}
