package com._32bit.project.cashier_system.DTO.saleItem;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

public class SaleItemInfoResponseSerializer extends JsonSerializer<SaleItemInfoResponseDto> {
    @Override
    public void serialize(SaleItemInfoResponseDto saleItemInfoResponseDto, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", saleItemInfoResponseDto.getId());
        jsonGenerator.writeStringField("ürün_adı", saleItemInfoResponseDto.getName());
        jsonGenerator.writeStringField("marka", saleItemInfoResponseDto.getBrand());
        jsonGenerator.writeNumberField("fıyat", saleItemInfoResponseDto.getPrice());

        if (saleItemInfoResponseDto.getDiscountedPrice() != 0.0) {
            jsonGenerator.writeNumberField("indirimli_fiyat", saleItemInfoResponseDto.getDiscountedPrice());
            jsonGenerator.writeNumberField("indirim_orani", saleItemInfoResponseDto.getDiscountRate());
        }
        jsonGenerator.writeNumberField("miktar", saleItemInfoResponseDto.getQuantity());

        if (saleItemInfoResponseDto.getForFree() != 0.0) {
            jsonGenerator.writeNumberField("ücretsiz", saleItemInfoResponseDto.getForFree());
        }
        jsonGenerator.writeNumberField("toplam", saleItemInfoResponseDto.getTotal());
        jsonGenerator.writeEndObject();
    }
}
