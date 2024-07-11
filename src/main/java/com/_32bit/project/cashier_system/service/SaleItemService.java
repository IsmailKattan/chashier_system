package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.saleItem.ItemOfSaleDto;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SaleItem;

import java.util.List;

public interface SaleItemService {

    List<SaleItem> getSaleItemsFromItemOfSaleDto(List<ItemOfSaleDto> items, Sale sale);

    SaleItem ApplyOffer(SaleItem saleItem);
}
