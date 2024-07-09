package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.offer.CreateOfferRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface OfferService {
    ResponseEntity<?> getOfferListByDeleted(Boolean deleted);
    ResponseEntity<?> getOfferByIdAndDeleted(Long id,Boolean deleted);
    ResponseEntity<?> createOffer(CreateOfferRequest request);
    ResponseEntity<?> updateOffer(Long id, CreateOfferRequest request);
    ResponseEntity<?> deleteOffer(Long id);
    ResponseEntity<?> restoreOffer(Long id);
    ResponseEntity<?> getOffersByNameContainsAndDeleted(String name, Boolean deleted);
    ResponseEntity<?> getOffersByDescriptionContainsAndDeleted(String description, Boolean deleted);
    ResponseEntity<?> getOffersByStartingDateBetweenAndDeleted(LocalDate startDate, LocalDate endDate, Boolean deleted);
}
