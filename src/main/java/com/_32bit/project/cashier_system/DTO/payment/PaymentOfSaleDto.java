package com._32bit.project.cashier_system.DTO.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentOfSaleDto {
    private String paymentMethod;
    private Double amount;
}
