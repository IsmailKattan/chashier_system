package com._32bit.project.cashier_system.DTO.invoice;


import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.saleItem.SaleItemInfoResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class InvoiceInfoDto {
    @JsonProperty("İşletme")
    private String salePointName;
    @JsonProperty("Adres")
    // end of first section in invoice
    private String salePointAddress;
    @JsonProperty("Satış Tarihi")
    private LocalDate saleDate;
    @JsonProperty("Satış Saati")
    private LocalTime saleTime;
    @JsonProperty("Satış No")
    private Long saleId;
    @JsonProperty("Kasiyer")
    private String teamMemberName;
    // end of second section in invoice
    @JsonProperty("Ürünler")
    private List<SaleItemInfoResponseDto> saleItems;
    // end of third section in invoice
    @JsonProperty("Toplam")
    private Double total;
    // end of fourth section in invoice
    @JsonProperty("Ödeme")
    private List<PaymentOfSaleDto> payments;
    // end of fifth section in invoice

    @JsonProperty("Fatura No")
    private Long id;
    @JsonProperty("Fatura Tarihi")
    private LocalDate extractionDate;
    @JsonProperty("Fatura Saati")
    private LocalTime extractionTime;


}
