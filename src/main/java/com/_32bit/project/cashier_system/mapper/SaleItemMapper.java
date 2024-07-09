package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.saleItem.ItemOfSaleDto;
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
                .ProductId(saleItem.getProduct().getId())
                .quantity(saleItem.getQuantity())
                .build();
    }


    public static SaleItem toSaleItemDomain(ItemOfSaleDto item) {
        return SaleItem.builder()
                .quantity(item.getQuantity())
                .build();
    }

}
