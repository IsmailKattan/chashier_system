package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.product.CreateProductRequest;
import com._32bit.project.cashier_system.DTO.product.ProductInfoResponse;
import com._32bit.project.cashier_system.domains.Product;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.enums.Category;
import com._32bit.project.cashier_system.domains.enums.Unit;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {
    public static ProductInfoResponse toProductInfoResponse(Product product) {
        return ProductInfoResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .category(product.getCategory().name())
                .quantity(product.getQuantity())
                .unit(product.getUnit().name())
                .price(product.getPrice())
                .isDiscounted(product.getIsDiscounted())
                .discountRate(product.getDiscountRate())
                .discountedPrice(product.getDiscountedPrice())
                .insertedBy(product.getInsertedBy().getUsername())
                .offerId(product.getOffer() != null ? product.getOffer().getId() : null)
                .offerName(product.getOffer() != null ? product.getOffer().getName() : null)
                .offerDescription(product.getOffer() != null ? product.getOffer().getDescription() : null)
                .build();
    }

    public static Product createProductRequestToProductDomain(CreateProductRequest request) {
        return Product.builder()
                .name(request.getName() != null ? request.getName() : "nullString")
                .brand(request.getBrand() != null ? request.getBrand() : "nullString")
                .category(request.getCategory()!= null ? Category.valueOf(request.getCategory().toUpperCase()) : Category.OTHER)
                .quantity(request.getQuantity() != null ? request.getQuantity() : 0.0)
                .unit(request.getUnit() != null ? Unit.valueOf(request.getUnit().toLowerCase()) : Unit.other)
                .price(request.getPrice() != null ? request.getPrice() : 0.0)
                .isDiscounted(false)
                .discountRate(0.0)
                .discountedPrice(0.0)
                .deleted(false)
                .offer(null)
                .build();
    }

    public static Object toProductsInfoResponse(List<Product> all) {
        List<ProductInfoResponse> productInfoResponses = new ArrayList<>();
        for (Product product : all) {
            productInfoResponses.add(toProductInfoResponse(product));
        }
        return productInfoResponses;
    }
}
