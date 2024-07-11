package com._32bit.project.cashier_system.DTO.saleItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonSerialize(using = SaleItemInfoResponseSerializer.class)
public class SaleItemInfoResponseDto {
    private Long id;
    @JsonProperty("fıyat")
    private Double price;
    @JsonProperty("indirimli_fiyat")
    private Double discountedPrice;
    @JsonProperty("miktar")
    private Double quantity;
    @JsonProperty("ücretsiz")
    private Double forFree;
    @JsonProperty("toplam")
    private Double total;
}
