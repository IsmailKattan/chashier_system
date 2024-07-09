package com._32bit.project.cashier_system.DTO.sale;

import com._32bit.project.cashier_system.DTO.saleItem.ItemOfSaleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CreateSaleRequest {
    private Long salePointId;
    private String paymentMethod;
    private List<ItemOfSaleDto> items;
}
