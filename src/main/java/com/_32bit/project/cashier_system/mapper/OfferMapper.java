package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.offer.CreateOfferRequest;
import com._32bit.project.cashier_system.DTO.offer.OfferInfoResponse;
import com._32bit.project.cashier_system.domains.Offer;

import java.time.LocalDate;
import java.util.List;

public class OfferMapper {
    public static OfferInfoResponse toOfferInfoResponse(Offer offer) {
        return OfferInfoResponse.builder()
                .id(offer.getId())
                .name(offer.getName() != null ? offer.getName() : "nullString")
                .description(offer.getDescription() != null ? offer.getDescription() : "nullString")
                .insertionDate(offer.getInsertionDate().toString())
                .startDate(offer.getStartDate() != null ? offer.getStartDate().toString() : "nullString")
                .endDate(offer.getEndDate() != null ? offer.getEndDate().toString() : "nullString")
                .getCount(offer.getGetCount() != null ? offer.getGetCount() : 0)
                .payFor(offer.getPayFor() != null ? offer.getPayFor() : 0)
                .build();

    }

    public static List<OfferInfoResponse> toOfferInfoResponseList(List<Offer> offerList) {
        return offerList.stream().map(OfferMapper::toOfferInfoResponse).toList();
    }

    public static Offer createOfferFromRequestToDomain(CreateOfferRequest request) {
        return Offer.builder()
                .name(request.getName() != null ? request.getName() : null)
                .description(request.getDescription() != null ? request.getDescription() : null)
                .insertionDate(LocalDate.now())
                .startDate(request.getStartDate() != null ? request.getStartDate() : null)
                .endDate(request.getEndDate() != null ? request.getEndDate() : null)
                .getCount(request.getGetCount() != null ? request.getGetCount() : null)
                .payFor(request.getPayFor() != null ? request.getPayFor() : null)
                .deleted(false)
                .build();
    }
}
