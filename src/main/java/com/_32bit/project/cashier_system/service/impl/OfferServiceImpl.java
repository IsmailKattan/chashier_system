package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.OfferRepository;
import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.offer.CreateOfferRequest;
import com._32bit.project.cashier_system.DTO.offer.OfferInfoResponse;
import com._32bit.project.cashier_system.domains.Offer;
import com._32bit.project.cashier_system.mapper.OfferMapper;
import com._32bit.project.cashier_system.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OfferServiceImpl implements OfferService {

    private final Logger logger = Logger.getLogger(OfferServiceImpl.class.getName());
    private final OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    @Override
    public ResponseEntity<?> getOfferListByDeleted(Boolean deleted) {
        if (deleted == null) {
            logger.warning("Deleted parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Deleted parameter is required"));
        }
        List<Offer> offerList = offerRepository.findAllByDeleted(deleted);
        if (offerList.isEmpty()) {
            logger.warning("No offers found");
            return ResponseEntity.notFound().build();
        }
        List<OfferInfoResponse> offerInfoResponseList = OfferMapper.toOfferInfoResponseList(offerList);
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offers found"),
                        offerInfoResponseList
                )
        );
    }

    @Override
    public ResponseEntity<?> getOfferByIdAndDeleted(Long id, Boolean deleted) {
        if(deleted == null) {
            logger.warning("Deleted parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Deleted parameter is required"));
        }
        if(id == null) {
            logger.warning("Id parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Id parameter is required"));
        }
        Optional<Offer> offerOptional = offerRepository.findByIdAndDeleted(id,deleted);
        if(offerOptional.isEmpty()) {
            logger.warning("Offer not found");
            return ResponseEntity.notFound().build();
        }
        OfferInfoResponse offerInfoResponse = OfferMapper.toOfferInfoResponse(offerOptional.get());
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offer found"),
                        offerInfoResponse
                )
        );


    }

    @Override
    @Transactional
    public ResponseEntity<?> createOffer(CreateOfferRequest request) {
        if(request == null) {
            logger.warning("Request body is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Request body is required"));
        }

        if(haveNullFields(request)) {
            logger.warning("Request body has null fields");
            return ResponseEntity.badRequest().body(new MessageResponse("Request body has null fields"));
        }

        if (request.getStartDate().isBefore(LocalDate.now())) {
            logger.warning("Start date cannot be before the current date");
            return ResponseEntity.badRequest().body(new MessageResponse("Start date cannot be before the current date"));
        }

        if (request.getEndDate().isBefore(request.getStartDate())) {
            logger.warning("End date cannot be before start date");
            return ResponseEntity.badRequest().body(new MessageResponse("End date cannot be before start date"));
        }

        Offer offer = null;
        try {
            offer = OfferMapper.createOfferFromRequestToDomain(request);
            offerRepository.save(offer);
        } catch (Exception e) {
            logger.warning("Error while creating offer");
            return ResponseEntity.badRequest().body(new MessageResponse("Error while creating offer"));
        }
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offer created"),
                        OfferMapper.toOfferInfoResponse(offer)
                )
        );
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateOffer(Long id, CreateOfferRequest request) {
        if (id == null) {
            logger.warning("Id parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Id parameter is required"));
        }
        if (request == null) {
            logger.warning("Request body is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Request body is required"));
        }
        if (haveNullFields(request)) {
            logger.warning("Request body has null fields");
            return ResponseEntity.badRequest().body(new MessageResponse("Request body has null fields"));
        }
        if (request.getStartDate().isBefore(LocalDate.now())) {
            logger.warning("Start date cannot be before the current date");
            return ResponseEntity.badRequest().body(new MessageResponse("Start date cannot be before the current date"));
        }
        if (request.getEndDate().isBefore(request.getStartDate())) {
            logger.warning("End date cannot be before start date");
            return ResponseEntity.badRequest().body(new MessageResponse("End date cannot be before start date"));
        }

        Optional<Offer> offerOptional = offerRepository.findById(id);
        if (offerOptional.isEmpty()) {
            logger.warning("Offer not found");
            return ResponseEntity.notFound().build();
        }
        Offer offer = offerOptional.get();
        try {
            offer.setId(id);
            offer.setName(request.getName());
            offer.setDescription(request.getDescription());
            offer.setStartDate(request.getStartDate());
            offer.setEndDate(request.getEndDate());
            offer.setGetCount(request.getGetCount());
            offer.setPayFor(request.getPayFor());
            offerRepository.save(offer);
        } catch (Exception e) {
            logger.warning("Error while updating offer");
            return ResponseEntity.badRequest().body(new MessageResponse("Error while updating offer"));
        }

        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offer updated"),
                        OfferMapper.toOfferInfoResponse(offer)
                )
        );
    }

    private boolean haveNullFields(CreateOfferRequest request) {
        return request.getName() == null ||
                request.getDescription() == null ||
                request.getStartDate() == null ||
                request.getEndDate() == null ||
                request.getGetCount() == null ||
                request.getPayFor() == null;
    }

    @Override
    public ResponseEntity<?> deleteOffer(Long id) {
        if (id == null) {
            logger.warning("Id parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Id parameter is required"));
        }
        Optional<Offer> offerOptional = offerRepository.findByIdAndDeleted(id, false);
        if (offerOptional.isEmpty()) {
            logger.warning("Offer not found");
            return ResponseEntity.notFound().build();
        }
        Offer offer = offerOptional.get();
        try {
            offer.setDeleted(true);
            offerRepository.save(offer);
        } catch (Exception e) {
            logger.warning("Error while deleting offer");
            return ResponseEntity.badRequest().body(new MessageResponse("Error while deleting offer"));
        }
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offer deleted"),
                        OfferMapper.toOfferInfoResponse(offer)
                )
        );
    }

    @Override
    public ResponseEntity<?> restoreOffer(Long id) {
        if (id == null) {
            logger.warning("Id parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Id parameter is required"));
        }
        Optional<Offer> offerOptional = offerRepository.findByIdAndDeleted(id, true);
        if (offerOptional.isEmpty()) {
            logger.warning("Offer not found");
            return ResponseEntity.notFound().build();
        }
        Offer offer = offerOptional.get();
        try {
            offer.setDeleted(false);
            offerRepository.save(offer);
        } catch (Exception e) {
            logger.warning("Error while restoring offer");
            return ResponseEntity.badRequest().body(new MessageResponse("Error while restoring offer"));
        }
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offer restored"),
                        OfferMapper.toOfferInfoResponse(offer)
                )
        );
    }

    @Override
    public ResponseEntity<?> getOffersByNameContainsAndDeleted(String name, Boolean deleted) {
        if (name == null) {
            logger.warning("Name parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Name parameter is required"));
        }
        if (deleted == null) {
            logger.warning("Deleted parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Deleted parameter is required"));
        }
        List<Offer> offerList = offerRepository.findByNameContainsAndDeleted(name, deleted);
        if (offerList.isEmpty()) {
            logger.warning("No offers found");
            return ResponseEntity.notFound().build();
        }
        List<OfferInfoResponse> offerInfoResponseList = OfferMapper.toOfferInfoResponseList(offerList);
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offers found"),
                        offerInfoResponseList
                )
        );
    }

    @Override
    public ResponseEntity<?> getOffersByDescriptionContainsAndDeleted(String description, Boolean deleted) {
        if (description == null) {
            logger.warning("Description parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Description parameter is required"));
        }
        if (deleted == null) {
            logger.warning("Deleted parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Deleted parameter is required"));
        }
        List<Offer> offerList = offerRepository.findByDescriptionContainsAndDeleted(description, deleted);
        if (offerList.isEmpty()) {
            logger.warning("No offers found");
            return ResponseEntity.notFound().build();
        }
        List<OfferInfoResponse> offerInfoResponseList = OfferMapper.toOfferInfoResponseList(offerList);
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offers found"),
                        offerInfoResponseList
                )
        );
    }

    @Override
    public ResponseEntity<?> getOffersByStartingDateBetweenAndDeleted(LocalDate startDate, LocalDate endDate, Boolean deleted) {
        if (startDate == null || endDate == null) {
            logger.warning("Start date and end date parameters are required");
            return ResponseEntity.badRequest().body(new MessageResponse("Start date and end date parameters are required"));
        }
        if (deleted == null) {
            logger.warning("Deleted parameter is required");
            return ResponseEntity.badRequest().body(new MessageResponse("Deleted parameter is required"));
        }
        List<Offer> offerList = offerRepository.findByStartDateBetweenAndDeleted(startDate, endDate, deleted);
        if (offerList.isEmpty()) {
            logger.warning("No offers found");
            return ResponseEntity.notFound().build();
        }
        List<OfferInfoResponse> offerInfoResponseList = OfferMapper.toOfferInfoResponseList(offerList);
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offers found"),
                        offerInfoResponseList
                )
        );
    }

    @Override
    public Offer getOfferDetailsById(Long id) {
        if (id == null) {
            logger.warning("Id parameter is required");
            return null;
        }
        Optional<Offer> offerOptional = offerRepository.findById(id);
        if (offerOptional.isEmpty()) {
            logger.warning("Offer not found");
            return null;
        }
        return offerOptional.get();
    }
}
