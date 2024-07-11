package com._32bit.project.cashier_system.DTO.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentOfSaleDto {
    @JsonProperty("odeme_yontemi")
    private String paymentMethod;
    @JsonProperty("miktar")
    private Double amount;
}
