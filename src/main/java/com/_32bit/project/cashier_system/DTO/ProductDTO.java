package com._32bit.project.cashier_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductDTO {

    private final long id;
    @Setter
    private String name;
    @Setter
    private String brand;
    @Setter
    private double quantity;
    @Setter
    private String unit;
    @Setter
    private double price;
    @Setter
    private boolean isDiscounted;
    @Setter
    private double discountRate;
    @Setter
    private double discountedPrice;
    @Setter
    private boolean deleted;
    @Setter
    private OfferDTO offer;
    private final TeamMemberDTO insertedBy;
}
