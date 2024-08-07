package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.offer.CreateOfferRequest;
import com._32bit.project.cashier_system.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getAllOffers() {
        return offerService.getOfferListByDeleted(false);
    }

    @GetMapping("/all-deleted")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getAllDeletedOffers() {
        return offerService.getOfferListByDeleted(true);
    }

    @GetMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getOffersByNameContains(@PathVariable String name) {
        return offerService.getOffersByNameContainsAndDeleted(name, false);
    }

    @GetMapping("/deleted-by-name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getDeletedOffersByNameContains(@PathVariable String name) {
        return offerService.getOffersByNameContainsAndDeleted(name, true);
    }

    @GetMapping("/by-description/{description}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getOffersByDescriptionContains(@PathVariable String description) {
        return offerService.getOffersByDescriptionContainsAndDeleted(description, false);
    }

    @GetMapping("/deleted-by-description/{description}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getDeletedOffersByDescriptionContains(@PathVariable String description) {
        return offerService.getOffersByDescriptionContainsAndDeleted(description, true);
    }

    @PostMapping("/by-start-date")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getOffersByStartDateAndEndDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return offerService.getOffersByStartingDateBetweenAndDeleted(startDate, endDate, false);
    }

    @PostMapping("/deleted-by-start-date")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getDeletedOffersByStartDateAndEndDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return offerService.getOffersByStartingDateBetweenAndDeleted(startDate, endDate, true);
    }

    @PostMapping("/create-offer")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> createOffer(@RequestBody CreateOfferRequest request) {
        return offerService.createOffer(request);
    }

    @PutMapping("/update-offer/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> updateOffer(@PathVariable Long id, @RequestBody CreateOfferRequest request) {
        return offerService.updateOffer(id, request);
    }

    @DeleteMapping("/delete-offer/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id) {
        return offerService.deleteOffer(id);
    }

    @PatchMapping("/restore-offer/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> restoreOffer(@PathVariable Long id) {
        return offerService.restoreOffer(id);
    }

    @GetMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getOfferById(@PathVariable Long id) {
        return offerService.getOfferByIdAndDeleted(id, false);
    }

    @GetMapping("/deleted-by-id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getDeletedOfferById(@PathVariable Long id) {
        return offerService.getOfferByIdAndDeleted(id, true);
    }



}
