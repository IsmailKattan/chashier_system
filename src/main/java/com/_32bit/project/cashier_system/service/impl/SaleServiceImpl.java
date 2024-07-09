package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.*;
import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.sale.CreateSaleRequest;
import com._32bit.project.cashier_system.DTO.sale.SaleInfoResponse;
import com._32bit.project.cashier_system.domains.*;
import com._32bit.project.cashier_system.domains.enums.PaymentMethod;
import com._32bit.project.cashier_system.mapper.SaleItemMapper;
import com._32bit.project.cashier_system.mapper.SaleMapper;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.PaymentService;
import com._32bit.project.cashier_system.service.SaleItemService;
import com._32bit.project.cashier_system.service.SaleService;
import com._32bit.project.cashier_system.service.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private final static Logger logger = LogManager.getLogger(SaleServiceImpl.class);
    private final SaleRepository saleRepository;
    private final TeamMemberRepository teamMemberRepository;

    private final PaymentService paymentService;
    private final SaleItemService saleItemService;

    private final SessionService sessionService;

    private final JwtUtils jwtUtils;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, TeamMemberRepository teamMemberRepository, PaymentService paymentService, SaleItemService saleItemService, SessionService sessionService, JwtUtils jwtUtils) {
        this.saleRepository = saleRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.paymentService = paymentService;
        this.saleItemService = saleItemService;
        this.sessionService = sessionService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> getSaleListByDeleted(Boolean deleted) {
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Deleted is null"), null));
        }
        List<Sale> sales = saleRepository.findAllByDeleted(deleted);
        if (sales.isEmpty()) {
            logger.error("Sales not found");
            return ResponseEntity.notFound().build();
        }
        List<SaleInfoResponse> response = SaleMapper.toSaleInfoResponseList(sales);

        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sales found"),
                        response
                )
        );


    }

    @Override
    public ResponseEntity<?> getSaleByIdAndDeleted(Long id, Boolean deleted) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Id is null"), null));
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Deleted is null"), null));
        }
        Optional<Sale> sale = saleRepository.findByIdAndDeleted(id, deleted);
        if (sale.isEmpty()) {
            logger.error("Sale not found");
            return ResponseEntity.notFound().build();
        }
        SaleInfoResponse response = SaleMapper.toSaleInfoResponse(sale.get());
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sale found"),
                        response
                )
        );
    }

    @Override
    public ResponseEntity<?> getSalePointSalesByDeleted(Long SalePointId, Boolean deleted) {
        if (SalePointId == null) {
            logger.error("SalePointId is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("SalePointId is null"), null));
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Deleted is null"), null));
        }
        List<Sale> sales = saleRepository.findSalesBySalePointId(SalePointId);
        if (sales.isEmpty()) {
            logger.error("Sales not found");
            return ResponseEntity.notFound().build();
        }
        List<SaleInfoResponse> response = SaleMapper.toSaleInfoResponseList(sales);
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sales found"),
                        response
                )
        );

    }

    @Override
    public ResponseEntity<?> getSessionSalesByDeleted(Long id, Boolean deleted) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Id is null"), null));
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Deleted is null"), null));
        }
        List<Sale> sales = saleRepository.findBySessionId(id);
        if (sales.isEmpty()) {
            logger.error("Sales not found");
            return ResponseEntity.notFound().build();
        }
        List<SaleInfoResponse> response = SaleMapper.toSaleInfoResponseList(sales);
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sales found"),
                        response
                )
        );
    }

    @Override
    public ResponseEntity<?> createSale(CreateSaleRequest request,String token) {
        if (request == null) {
            logger.error("Request is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Request is null"), null));
        }
        if (haveNullFields(request)) {
            logger.error("Request have null fields");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Request have null fields"), null));
        }
        String username = jwtUtils.getUsernameFromJwtToken(token.substring(7));
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByUsername(username);
        if (teamMemberOptional.isEmpty()) {
            logger.error("Team member not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectWithMessageResponse(new MessageResponse("Team member not found"), null));
        }
        TeamMember teamMember = teamMemberOptional.get();
        if (teamMember.getSalePoint() == null) {
            logger.error("Sale point not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectWithMessageResponse(new MessageResponse("Sale point not found"), null));
        }
        Session session = sessionService.getOpenSessionOfSalePoint(teamMember.getSalePoint().getId());

        if (session == null) {
            logger.error("Session not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectWithMessageResponse(new MessageResponse("Session not found"), null));
        }

        if (session.getClosed()) {
            logger.error("Session is closed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjectWithMessageResponse(new MessageResponse("Session is closed"), null));
        }

        Sale sale = SaleMapper.createSaleRequestToSale(request,teamMember,session);
        List<SaleItem> saleItems = saleItemService.getSaleItemsFromItemOfSaleDto(request.getItems(),sale);
        sale.setTotal(
                saleItems.stream().mapToDouble(SaleItem::getTotal).sum()
        );

        sale.setSaleItems(saleItems);
        saleRepository.save(sale);
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sale created"),
                        SaleMapper.toSaleInfoResponse(sale)
                )
        );
    }

    private boolean haveNullFields(CreateSaleRequest request) {
        return request.getSalePointId() == null ||  request.getItems() == null || request.getPaymentMethod() == null;
    }

    @Override
    public ResponseEntity<?> payment(Long saleId, List<PaymentOfSaleDto> request) {
        if (saleId == null) {
            logger.error("SaleId is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("SaleId is null"), null));
        }
        if (request == null) {
            logger.error("Request is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Request is null"), null));
        }
        if (request.isEmpty()) {
            logger.error("Request is empty");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Request is empty"), null));
        }
        Optional<Sale> saleOptional = saleRepository.findById(saleId);
        if (saleOptional.isEmpty()) {
            logger.error("Sale not found");
            return ResponseEntity.notFound().build();
        }
        Sale sale = saleOptional.get();
        if (sale.getIsPaid()) {
            logger.error("Sale is already paid");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is already paid"), null));
        }
        if (sale.getIsInvoiced()) {
            logger.error("Sale is invoiced");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is invoiced"), null));
        }
        if (sale.getIsPosted()) {
            logger.error("Sale is posted");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is posted"), null));
        }
        if (sale.getTotal() < request.stream().mapToDouble(PaymentOfSaleDto::getAmount).sum()) {
            logger.error("Payment amount is greater than total");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Payment amount is greater than total"), null));
        }
        List<Payment> payments = paymentService.getPaymentsFromPaymentOfSaleDto(request, sale);
        sale.setPayments(payments);
        sale.setIsPaid(true);
        saleRepository.save(sale);
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Payment successful"),
                        SaleMapper.toSaleInfoResponse(sale)
                )
        );

    }

    @Override
    public ResponseEntity<?> invoice(Long saleId) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateSale(Long id, CreateSaleRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteSale(Long id) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Id is null"), null));
        }
        Optional<Sale> saleOptional = saleRepository.findById(id);
        if (saleOptional.isEmpty()) {
            logger.error("Sale not found");
            return ResponseEntity.notFound().build();
        }
        Sale sale = saleOptional.get();
        sale.setDeleted(true);
        saleRepository.save(sale);
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sale deleted"),
                        SaleMapper.toSaleInfoResponse(sale)
                )
        );
    }

    @Override
    public ResponseEntity<?> restoreSale(Long id) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Id is null"), null));
        }
        Optional<Sale> saleOptional = saleRepository.findById(id);
        if (saleOptional.isEmpty()) {
            logger.error("Sale not found");
            return ResponseEntity.notFound().build();
        }
        Sale sale = saleOptional.get();
        sale.setDeleted(false);
        saleRepository.save(sale);
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sale restored"),
                        SaleMapper.toSaleInfoResponse(sale)
                )
        );
    }
}
