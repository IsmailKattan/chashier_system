package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.SaleItemRepository;
import com._32bit.project.cashier_system.DTO.saleItem.ItemOfSaleDto;
import com._32bit.project.cashier_system.domains.Offer;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SaleItem;
import com._32bit.project.cashier_system.service.SaleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleItemServiceImpl implements SaleItemService {

    private final SaleItemRepository saleItemRepository;

    private final ProductServiceImpl productService;

    @Autowired
    public SaleItemServiceImpl(SaleItemRepository saleItemRepository, ProductServiceImpl productService) {
        this.saleItemRepository = saleItemRepository;
        this.productService = productService;
    }

    @Override
    public List<SaleItem> getSaleItemsFromItemOfSaleDto(List<ItemOfSaleDto> items, Sale sale) {
        if (items == null) {
            return null;
        }
        List<SaleItem> saleItems = new ArrayList<>();
        for (ItemOfSaleDto item : items) {
            SaleItem saleItem = SaleItem.builder()
                    .quantity(item.getQuantity()!=null ? item.getQuantity() : 0)
                    .deleted(false)
                    .sale(sale)
                    .product(productService.getProductById(item.getProductId()) != null ? productService.getProductById(item.getProductId()) : productService.getProductById(0L))
                    .build();
                    saleItem.setTotal(saleItem.getQuantity() * productService.getPriceById(item.getProductId()));

                    if (productHaveOffer(item.getProductId())) {
                        saleItem = ApplyOffer(saleItem);
                    }
            saleItems.add(saleItem);
        }
        saleItemRepository.saveAll(saleItems);
        return saleItems;
    }

    @Override
    public Boolean productHaveOffer(Long productId) {
        if (productId == null) {
            return false;
        }
        return productService.getProductById(productId).getOffer() != null;
    }

    @Override
    public SaleItem ApplyOffer(SaleItem saleItem) {
        if (saleItem == null) {
            return null;
        }
        if (productHaveOffer(saleItem.getProduct().getId())) {
            Offer offerDetails = productService.getOfferDetails(saleItem.getProduct().getId());
            saleItem.setTotal(offerDetails.getPayFor() * saleItem.getQuantity());
        }
        return saleItem;
    }
}
