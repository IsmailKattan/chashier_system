package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.saleItem.ItemOfSaleDto;
import com._32bit.project.cashier_system.DTO.saleItem.SaleItemInfoResponseDto;
import com._32bit.project.cashier_system.domains.SaleItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SaleItemMapper {


    public static List<ItemOfSaleDto> toItemOfSaleDtoList(List<SaleItem> saleItems) {

        return saleItems.stream()
                .map(SaleItemMapper::toItemOfSaleDto)
                .collect(Collectors.toList());
    }

    private static ItemOfSaleDto toItemOfSaleDto(SaleItem saleItem) {
        return ItemOfSaleDto.builder()
                .productId(saleItem.getProduct().getId())
                .quantity(saleItem.getQuantity())
                .build();
    }

    public static List<SaleItemInfoResponseDto> toSaleItemInfoResponseDtoList(List<SaleItem> saleItems) {
        List<SaleItemInfoResponseDto> saleItemInfoResponseDtos = new ArrayList<>();
        for (SaleItem saleItem : saleItems) {
            saleItemInfoResponseDtos.add(
                    SaleItemInfoResponseDto.builder()
                            .id(saleItem.getId())
                            .name(saleItem.getProduct().getName())
                            .brand(saleItem.getProduct().getBrand())
                            .price(saleItem.getProduct().getPrice())
                            .discountedPrice(saleItem.getProduct().getDiscountedPrice())
                            .discountRate(saleItem.getProduct().getDiscountRate())
                            .quantity(saleItem.getQuantity())
                            .unit(saleItem.getProduct().getUnit().name())
                            .forFree(saleItem.getQuantity() - saleItem.getPaidFor())
                            .total(saleItem.getTotal())
                            .build()
            );
        }

        return saleItemInfoResponseDtos;

    }
}
