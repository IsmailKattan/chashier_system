package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.SaleItemRepository;
import com._32bit.project.cashier_system.DTO.saleItem.ItemOfSaleDto;
import com._32bit.project.cashier_system.domains.Offer;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SaleItem;
import com._32bit.project.cashier_system.service.OfferService;
import com._32bit.project.cashier_system.service.ProductService;
import com._32bit.project.cashier_system.service.SaleItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleItemServiceImpl implements SaleItemService {

    private final static Logger logger = LogManager.getLogger(SaleItemServiceImpl.class);

    private final SaleItemRepository saleItemRepository;

    private final ProductService productService;

    private final OfferService offerService;

    @Autowired
    public SaleItemServiceImpl(SaleItemRepository saleItemRepository, ProductServiceImpl productService, OfferService offerService) {
        this.saleItemRepository = saleItemRepository;
        this.productService = productService;
        this.offerService = offerService;
    }

    @Override
    public List<SaleItem> getSaleItemsFromItemOfSaleDto(List<ItemOfSaleDto> items, Sale sale) {
        // if items is null return an empty list
        if (items == null) {
            return new ArrayList<>();
        }
        // create a list of sale items
        List<SaleItem> saleItems = new ArrayList<>();
        // handle each item in the list
        for (ItemOfSaleDto item : items) {
            // if the item is unable to be added, skip it
            if (unableToBeAdded(item)) {
                continue;
            } else {
                // create a sale item
                SaleItem saleItem = SaleItem.builder()
                        .quantity(item.getQuantity()!=null ? item.getQuantity() : 0)
                        .deleted(false)
                        .sale(sale)
                        .isOfferApplied(false)
                        .product(productService.getProductById(item.getProductId()))
                        .isDiscounted(productService.getProductById(item.getProductId()).getIsDiscounted())
                        .paidFor(item.getQuantity() != null ? item.getQuantity() : 0)
                        .build();

                // if the product has an offer, apply the offer else set the total to the quantity * price
                if (productService.productHaveOffer(item.getProductId())) {
                    saleItem.setOffer(productService.getOfferDetails(item.getProductId()));
                    saleItem = ApplyOffer(saleItem);
                }
                else{
                    saleItem.setTotal(saleItem.getQuantity() * productService.getPriceById(item.getProductId()));
                }
                productService.decreaseQuantity(item.getProductId(), item.getQuantity());
                saleItems.add(saleItem);
            }
        }
        saleItemRepository.saveAll(saleItems);
        return saleItems;
    }

    private boolean unableToBeAdded(ItemOfSaleDto item) {
        boolean check = false;
        if (item == null) {
            logger.error("Item is null");
            return true;
        }
        if (item.getProductId() == null) {
            logger.error("Product id is null: " + item.toString());
            return true;
        }
        if (item.getQuantity() == null) {
            logger.error("Quantity is null: " + item.toString());
            return true;
        }
        if (item.getQuantity() <= 0) {
            logger.error("Quantity is less than or equal to 0: " + item.toString());
            return true;
        }
        if (productService.getProductById(item.getProductId()) == null) {
            logger.error("Product is null: " + item.toString());
            return true;
        }
        if (productService.getProductById(item.getProductId()).getQuantity() < item.getQuantity()) {
            logger.error("Product quantity is less than requested quantity, Requested: " + item.getQuantity() + " Available: " + productService.getProductById(item.getProductId()).getQuantity() + " Product: " + productService.getProductById(item.getProductId()).getName() + " " + productService.getProductById(item.getProductId()).getBrand());
            return true;
        }
        return check;
    }

    @Override
    public SaleItem ApplyOffer(SaleItem saleItem) {
        if (saleItem == null) {
            logger.error("Sale item is null");
            return null;
        }
        // get the offer details
        Offer offerDetails = offerService.getOfferDetailsById(saleItem.getOffer().getId());
        if (offerDetails == null) {
            logger.error("Offer details are null");
            return saleItem;
        }
        // if the offer details are null, return the sale item
        if (saleItem.getQuantity() < offerDetails.getGetCount()) {
            saleItem.setTotal(saleItem.getQuantity() * productService.getPriceById(saleItem.getProduct().getId()));
        }
        // if the quantity is greater than or equal to the getCount
        if (saleItem.getQuantity() >= offerDetails.getGetCount()) {
            // quantity will be paid for: p/g * (q - q%g) + q%g
            // where p is payFor,
            // g is getCount, q
            // is quantity

            Double quantity = offerDetails.getPayFor() / offerDetails.getGetCount() * (saleItem.getQuantity() - saleItem.getQuantity() % offerDetails.getGetCount()) + saleItem.getQuantity() % offerDetails.getGetCount();
            saleItem.setTotal(quantity * productService.getPriceById(saleItem.getProduct().getId()));
            saleItem.setPaidFor(quantity);
        }
        saleItem.setIsOfferApplied(true);
        return saleItem;
    }
}
