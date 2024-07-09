package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.offer.CreateOfferRequest;
import com._32bit.project.cashier_system.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/offer")
@RequiredArgsConstructor
public class OfferResource {

    private final OfferService offerService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Offer Resource");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOffers() {
        return offerService.getOfferListByDeleted(false);
    }

    @GetMapping("/all-deleted")
    public ResponseEntity<?> getAllDeletedOffers() {
        return offerService.getOfferListByDeleted(true);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<?> getOffersByNameContains(@PathVariable String name) {
        return offerService.getOffersByNameContainsAndDeleted(name, false);
    }

    @GetMapping("/deleted-by-name/{name}")
    public ResponseEntity<?> getDeletedOffersByNameContains(@PathVariable String name) {
        return offerService.getOffersByNameContainsAndDeleted(name, true);
    }

    @GetMapping("/by-description/{description}")
    public ResponseEntity<?> getOffersByDescriptionContains(@PathVariable String description) {
        return offerService.getOffersByDescriptionContainsAndDeleted(description, false);
    }

    @GetMapping("/deleted-by-description/{description}")
    public ResponseEntity<?> getDeletedOffersByDescriptionContains(@PathVariable String description) {
        return offerService.getOffersByDescriptionContainsAndDeleted(description, true);
    }

    @PostMapping("/by-start-date")
    public ResponseEntity<?> getOffersByStartDateAndEndDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return offerService.getOffersByStartingDateBetweenAndDeleted(startDate, endDate, false);
    }

    @PostMapping("/deleted-by-start-date")
    public ResponseEntity<?> getDeletedOffersByStartDateAndEndDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return offerService.getOffersByStartingDateBetweenAndDeleted(startDate, endDate, true);
    }

    @PostMapping("/create-offer")
    public ResponseEntity<?> createOffer(@RequestBody CreateOfferRequest request) {
        return offerService.createOffer(request);
    }

    @PutMapping("/update-offer/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable Long id, @RequestBody CreateOfferRequest request) {
        return offerService.updateOffer(id, request);
    }

    @DeleteMapping("/delete-offer/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id) {
        return offerService.deleteOffer(id);
    }

    @PatchMapping("/restore-offer/{id}")
    public ResponseEntity<?> restoreOffer(@PathVariable Long id) {
        return offerService.restoreOffer(id);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> getOfferById(@PathVariable Long id) {
        return offerService.getOfferByIdAndDeleted(id, false);
    }

    @GetMapping("/deleted-by-id/{id}")
    public ResponseEntity<?> getDeletedOfferById(@PathVariable Long id) {
        return offerService.getOfferByIdAndDeleted(id, true);
    }



}
