package com._32bit.project.cashier_system.DTO.sale;

import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.saleItem.ItemOfSaleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class SaleInfoResponse {
    private Long salePointId;
    private Long sessionId;
    private Long id;
    private LocalDate saleDate;
    private LocalTime saleTime;
    private List<ItemOfSaleDto> items;
    private List<PaymentOfSaleDto> payments;
    private Double total;
    private Boolean isPaid;
    private Boolean isPosted;
    private Boolean isInvoiced;
    private String soldById;
}
