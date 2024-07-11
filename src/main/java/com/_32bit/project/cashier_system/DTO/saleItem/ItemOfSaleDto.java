package com._32bit.project.cashier_system.DTO.saleItem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ItemOfSaleDto {
    private Long productId;
    private Double quantity;
}
