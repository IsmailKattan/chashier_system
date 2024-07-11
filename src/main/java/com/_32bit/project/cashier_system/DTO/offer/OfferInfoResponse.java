package com._32bit.project.cashier_system.DTO.offer;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OfferInfoResponse {
    private Long id;
    private String name;
    private String description;
    private String insertionDate;
    private String startDate;
    private String endDate;
    private Double getCount;
    private Double payFor;
}
