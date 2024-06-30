package com._32bit.project.cashier_system.DTO.salePoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CreateSalePointRequest {
    @JsonIgnore
    private Long id;
    private String name;
    private String address;
}
