package com._32bit.project.cashier_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class SaleItemDTO {

    private final long id;
    @Setter
    private double quantity;
    @Setter
    private double total;
    @Setter
    private double discountRate;
    @Setter
    private double discountedPrice;
    @Setter
    private boolean deleted;
    private final SaleDTO sale;
    private final ProductDTO product;
}
