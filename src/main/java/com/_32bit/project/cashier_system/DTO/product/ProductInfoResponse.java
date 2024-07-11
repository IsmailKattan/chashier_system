package com._32bit.project.cashier_system.DTO.product;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoResponse {
    Long id;
    String name;
    String brand;
    String category;
    Double quantity;
    String unit;
    Double price;
    Boolean isDiscounted;
    Double discountRate;
    Double discountedPrice;
    String insertedBy;
    Long offerId;
    String offerName;
    String offerDescription;

}
