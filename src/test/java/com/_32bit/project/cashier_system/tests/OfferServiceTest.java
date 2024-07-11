package com._32bit.project.cashier_system.tests;

import com._32bit.project.cashier_system.DAO.OfferRepository;
import com._32bit.project.cashier_system.DTO.offer.CreateOfferRequest;
import com._32bit.project.cashier_system.domains.Offer;
import com._32bit.project.cashier_system.service.impl.OfferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferServiceImpl offerServiceImpl;

    private Offer offer;

    private LocalDate startDate;
    private LocalDate endDate;

    private CreateOfferRequest createOfferRequest;

    @BeforeEach
    public void setup() {
        offer = new Offer();
        offer.setId(1L);
        offer.setName("Test Offer");
        offer.setDescription("Test Description");
        offer.setStartDate(LocalDate.parse("2025-01-01"));
        offer.setEndDate(LocalDate.parse("2027-12-31"));
        offer.setInsertionDate(LocalDate.now());
        offer.setGetCount(1);
        offer.setPayFor(1);
        offer.setDeleted(false);

        createOfferRequest = new CreateOfferRequest();
        createOfferRequest.setName("Test Offer");
        createOfferRequest.setDescription("Test Description");
        createOfferRequest.setStartDate(LocalDate.parse("2025-01-01"));
        createOfferRequest.setEndDate(LocalDate.parse("2027-12-31"));
        createOfferRequest.setGetCount(1);
        createOfferRequest.setPayFor(1);

        startDate = LocalDate.of(2022, 1, 1);
        endDate = LocalDate.of(2022, 12, 31);
    }

    @Test
    public void testGetOfferListByDeleted() {
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);

        when(offerRepository.findAllByDeleted(false)).thenReturn(offers);

        ResponseEntity<?> response = offerServiceImpl.getOfferListByDeleted(false);

        assertEquals(200, response.getStatusCodeValue());
        verify(offerRepository, times(1)).findAllByDeleted(false);
    }

    @Test
    public void testGetOfferByIdAndDeleted() {
        when(offerRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(offer));

        ResponseEntity<?> response = offerServiceImpl.getOfferByIdAndDeleted(1L, false);

        assertEquals(200, response.getStatusCodeValue());
        verify(offerRepository, times(1)).findByIdAndDeleted(1L, false);
    }

    @Test
    public void testCreateOffer() {
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        ResponseEntity<?> response = offerServiceImpl.createOffer(createOfferRequest);

        assertEquals(200, response.getStatusCodeValue());
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    public void testUpdateOffer() {
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        ResponseEntity<?> response = offerServiceImpl.updateOffer(1L, createOfferRequest);

        assertEquals(200, response.getStatusCodeValue());
        verify(offerRepository, times(1)).findById(1L);
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    public void testDeleteOffer() {
        when(offerRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        ResponseEntity<?> response = offerServiceImpl.deleteOffer(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(offerRepository, times(1)).findByIdAndDeleted(1L, false);
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    public void testRestoreOffer() {
        when(offerRepository.findByIdAndDeleted(1L, true)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        ResponseEntity<?> response = offerServiceImpl.restoreOffer(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(offerRepository, times(1)).findByIdAndDeleted(1L, true);
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    public void testGetOffersByNameContainsAndDeleted() {
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);

        when(offerRepository.findByNameContainsAndDeleted("Test", false)).thenReturn(offers);

        ResponseEntity<?> response = offerServiceImpl.getOffersByNameContainsAndDeleted("Test", false);

        assertEquals(200, response.getStatusCodeValue());
        verify(offerRepository, times(1)).findByNameContainsAndDeleted("Test", false);
    }

    @Test
    public void testGetOffersByDescriptionContainsAndDeleted() {
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);

        when(offerRepository.findByDescriptionContainsAndDeleted("Description", false)).thenReturn(offers);

        ResponseEntity<?> response = offerServiceImpl.getOffersByDescriptionContainsAndDeleted("Description", false);

        assertEquals(200, response.getStatusCodeValue());
        verify(offerRepository, times(1)).findByDescriptionContainsAndDeleted("Description", false);
    }

    @Test
    public void testGetOffersByStartingDateBetweenAndDeleted() {
        when(offerRepository.findByStartDateBetweenAndDeleted(startDate, endDate, false))
                .thenReturn(Arrays.asList(offer));

        ResponseEntity<?> response = offerServiceImpl.getOffersByStartingDateBetweenAndDeleted(startDate, endDate, false);

        assertEquals(200, response.getStatusCodeValue());
        verify(offerRepository, times(1)).findByStartDateBetweenAndDeleted(startDate, endDate, false);
    }
}
