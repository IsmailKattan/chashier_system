package com._32bit.project.cashier_system.DTO.saleItem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemOfSaleDto {
    private Long ProductId;
    private Double quantity;
}
