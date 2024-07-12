package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.*;
import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.sale.CreateSaleRequest;
import com._32bit.project.cashier_system.DTO.sale.SaleInfoResponse;
import com._32bit.project.cashier_system.DTO.sale.SaleSpecification;
import com._32bit.project.cashier_system.domains.*;
import com._32bit.project.cashier_system.domains.enums.PaymentMethod;
import com._32bit.project.cashier_system.mapper.SaleItemMapper;
import com._32bit.project.cashier_system.mapper.SaleMapper;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class SaleServiceImpl implements SaleService {

    private final static Logger logger = LogManager.getLogger(SaleServiceImpl.class);
    private final SaleRepository saleRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PaymentService paymentService;
    private final SaleItemService saleItemService;
    private final SessionService sessionService;
    private final JwtUtils jwtUtils;
    private final InvoiceService invoiceService;
    private final SalePointService salePointService;
    private final ProductService productService;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, TeamMemberRepository teamMemberRepository, PaymentService paymentService, SaleItemService saleItemService, SessionService sessionService, JwtUtils jwtUtils, InvoiceService invoiceService, SalePointService salePointService, ProductService productService) {
        this.saleRepository = saleRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.paymentService = paymentService;
        this.saleItemService = saleItemService;
        this.sessionService = sessionService;
        this.jwtUtils = jwtUtils;
        this.invoiceService = invoiceService;
        this.salePointService = salePointService;
        this.productService = productService;
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
        logger.info("Sales deleted(" + deleted + ") found and returned successfully");
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
        logger.info("Sale with id " + id + " found");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sale found"),
                        response
                )
        );
    }

    @Override
    public Sale getSaleObjectByIdAndDeleted(Long id, Boolean deleted) {
        if (id == null) {
            logger.error("Id is null");
            return null;
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return null;
        }

        Optional<Sale> sale = saleRepository.findByIdAndDeleted(id, deleted);
        if (sale.isEmpty()) {
            logger.error("Sale not found");
            return null;
        }
        return sale.get();
    }

    @Override
    public ResponseEntity<?> getSalePointSales(Long SalePointId) {
        if (SalePointId == null) {
            logger.error("SalePointId is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("SalePointId is null"), null));
        }
        List<Sale> sales = saleRepository.findSalesBySalePointId(SalePointId);
        if (sales.isEmpty()) {
            logger.error("Sales not found");
            return ResponseEntity.notFound().build();
        }
        List<SaleInfoResponse> response = SaleMapper.toSaleInfoResponseList(sales);
        logger.info("Sale point with id: " + SalePointId + " sales found");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sales found"),
                        response
                )
        );


    }

    public List<Sale> getSalesBySalePointId(Long salePointId) {
        if (salePointId == null) {
            logger.error("SalePointId is null");
            return new ArrayList<>();
        }
        return saleRepository.findSalesBySalePointId(salePointId);
    }

    @Override
    public ResponseEntity<?> getSessionSales(Long id) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Id is null"), null));
        }
        List<Sale> sales = saleRepository.findBySessionIdAndDeleted(id,false);
        if (sales.isEmpty()) {
            logger.error("Sales not found");
            return ResponseEntity.notFound().build();
        }
        List<SaleInfoResponse> response = SaleMapper.toSaleInfoResponseList(sales);
        logger.info("session with id: " + id + " sales found");
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

        Optional<SalePoint> salePointOptional = sessionService.getSalePointById(request.getSalePointId());

        if (salePointOptional.isEmpty()) {
            logger.error("Sale point not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectWithMessageResponse(new MessageResponse("Sale point not found"), null));
        }

        Session session = sessionService.getOpenSessionOfSalePoint(salePointOptional.get().getId());

        if (session == null) {
            logger.error("Open session not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectWithMessageResponse(new MessageResponse("Open Session not found"), null));
        }

        if (salePointOptional.get().getDeleted()) {
            logger.error("Sale point is deleted");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjectWithMessageResponse(new MessageResponse("Sale point is deleted"), null));
        }

        if (teamMember.getSalePoint() != session.getSalePoint() && teamMember.getSalePoint() != null) {
            logger.error("Team member is not authorized to create sale");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ObjectWithMessageResponse(new MessageResponse("Team member is not authorized to create sale"), null));
        }


        if (session.getClosed()) {
            logger.error("Session is closed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjectWithMessageResponse(new MessageResponse("Session is closed"), null));
        }

        Sale sale = SaleMapper.createSaleRequestToSale(request,teamMember,session);
        saleRepository.save(sale);
        List<SaleItem> saleItems = saleItemService.getSaleItemsFromItemOfSaleDto(request.getItems(),sale);
        sale.setSaleItems(saleItems);
        sale.setTotal(
                saleItems.stream().mapToDouble(SaleItem::getTotal).sum()
        );


        saleRepository.save(sale);
        logger.info("Sale created");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sale created"),
                        SaleMapper.toSaleInfoResponse(sale)
                )
        );
    }

    @Override
    public void postSessionSales(Long sessionId) {
        if (sessionId == null) {
            logger.error("SessionId is null");
            return;
        }
        List<Sale> sales = sessionService.getSessionSalesBySessionId(sessionId);
        if (sales.isEmpty()) {
            logger.error("Sales not found");
            return;
        }
        for (Sale sale : sales) {
            sale.setIsPosted(true);
            saleRepository.save(sale);
        }
    }

    private boolean haveNullFields(CreateSaleRequest request) {
        return request.getSalePointId() == null ||  request.getItems() == null ;
    }

    @Override
    public ResponseEntity<?> payment(Long saleId, List<PaymentOfSaleDto> request) {
        // check if saleId is null
        if (saleId == null) {
            logger.error("SaleId is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("SaleId is null"), null));
        }
        // check if request is null
        if (request == null) {
            logger.error("Request is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Request is null"), null));
        }
        // check if request is empty
        if (request.isEmpty()) {
            logger.error("Request is empty");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Request is empty"), null));
        }
        // check if sale with saleId exists
        Optional<Sale> saleOptional = saleRepository.findByIdAndDeleted(saleId,false);
        if (saleOptional.isEmpty()) {
            logger.error("Sale with id: " + saleId + " not found");
            return ResponseEntity.notFound().build();
        }
        // check if sale is already paid
        Sale sale = saleOptional.get();
        if (sale.getIsPaid()) {
            logger.error("Sale with id: " + saleId + " is already paid");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is already paid"), null));
        }
        //
        if (sale.getIsInvoiced()) {
            logger.error("Sale with id: " + saleId + " is invoiced");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is invoiced"), null));
        }
        // check if sale is posted
        if (sale.getIsPosted()) {
            logger.error("Sale with id: " + saleId + " is posted");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is posted"), null));
        }
        // check if sale is deleted
        if (sale.getTotal() < request.stream().mapToDouble(PaymentOfSaleDto::getAmount).sum()) {
            logger.error("Payment amount is greater than total of sale with id: " + saleId);
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Payment amount is greater than total"), null));
        }
        if (sale.getTotal() > request.stream().mapToDouble(PaymentOfSaleDto::getAmount).sum()) {

            logger.error("Payment amount is less than total of sale with id: " + saleId);
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Payment amount is less than total"), null));
        }
        for (PaymentOfSaleDto paymentOfSaleDto : request) {
            if (paymentOfSaleDto.getPaymentMethod() == null || paymentOfSaleDto.getAmount() == null) {
                logger.error("Payment method or amount is null");
                return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Payment method or amount is null"), null));
            }
            if (paymentOfSaleDto.getAmount() <= 0) {
                logger.error("Payment amount is less than or equal to 0");
                return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Payment amount is less than or equal to 0"), null));
            }
            if (!PaymentMethod.contains(paymentOfSaleDto.getPaymentMethod().toLowerCase())) {
                logger.error("Payment method is not valid");
                return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Payment method is not valid"), null));
            }
        }

        List<Payment> payments = paymentService.getPaymentsFromPaymentOfSaleDto(request, sale);
//        sale.setPayments(payments);
        sale.setIsPaid(true);
        saleRepository.save(sale);
        logger.info("Payment of sale with id: " + saleId + " successful");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Payment successful"),
                        SaleMapper.toSaleInfoResponse(sale)
                )
        );

    }

    @Override
    public ResponseEntity<?> invoice(Long saleId) {
        if (saleId == null) {
            logger.error("SaleId is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("SaleId is null"), null));
        }
        Optional<Sale> saleOptional = saleRepository.findById(saleId);
        if (saleOptional.isEmpty()) {
            logger.error("Sale with id: " + saleId + " not found");
            return ResponseEntity.notFound().build();
        }
        Sale sale = saleOptional.get();
        if (sale.getIsInvoiced()) {
            logger.error("Sale with id: " + saleId + " is already invoiced");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is already invoiced"), null));
        }
        if (!sale.getIsPaid()) {
            logger.error("Sale with id: " + saleId + " is not paid");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is not paid"), null));
        }
        if (sale.getIsPosted()) {
            logger.error("Sale with id: " + saleId + " is posted");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is posted"), null));
        }
        TeamMember SoldBy = sale.getSoldBy();
        if (SoldBy == null) {
            logger.error("SoldBy is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("SoldBy is null"), null));
        }
        if (SoldBy.getDeleted()) {
            logger.error("SoldBy is deleted");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("SoldBy is deleted"), null));
        }
        if (sale.getDeleted()) {
            logger.error("Sale is deleted");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is deleted"), null));
        }
        SalePoint salePoint = this.getSalePointBySaleId(saleId);
        invoiceService.creaInvoice(sale, SoldBy, salePoint);
        sale.setIsInvoiced(true);
        saleRepository.save(sale);
        logger.info("Invoice created");
        return invoiceService.getInvoiceBySaleId(saleId);
    }

    @Override
    public ResponseEntity<?> updateSale(Long id, CreateSaleRequest request) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Id is null"), null));
        }
        if (request == null) {
            logger.error("Request is null");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Request is null"), null));
        }
        if (haveNullFields(request)) {
            logger.error("Request have null fields");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Request have null fields"), null));
        }
        Optional<Sale> saleOptional = saleRepository.findById(id);
        if (saleOptional.isEmpty()) {
            logger.error("Sale with id: " + id + " not found");
            return ResponseEntity.notFound().build();
        }
        Sale sale = saleOptional.get();
        if (sale.getIsPaid()) {
            logger.error("cannot update paid sale, sale with id: " + id + " is paid");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Can't update paid sale"), null));
        }
        if (sale.getIsInvoiced()) {
            logger.error("cannot update invoiced sale, sale with id: " + id + " is invoiced");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Can't update invoiced sale"), null));
        }
        if (sale.getIsPosted()) {
            logger.error("cannot update posted sale, sale with id: " + id + " is posted");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Can't update posted sale"), null));
        }
        if (sale.getDeleted()) {
            logger.error("cannot update deleted sale, sale with id: " + id + " is deleted");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Sale is deleted"), null));
        }
        sale.setSaleItems(saleItemService.getSaleItemsFromItemOfSaleDto(request.getItems(), sale));
        sale.setTotal(
                sale.getSaleItems().stream().mapToDouble(SaleItem::getTotal).sum()
        );
        saleRepository.save(sale);
        logger.info("Sale with id: " + id + " updated");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sale updated"),
                        SaleMapper.toSaleInfoResponse(sale)
                )
        );
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
        if (sale.getIsPaid()) {
            logger.error("cannot delete paid sale, sale with id: " + id + " is paid");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Can't delete paid sale"), null));
        }
        if (sale.getIsInvoiced()) {
            logger.error("cannot delete invoiced sale, sale with id: " + id + " is invoiced");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Can't delete invoiced sale"), null));
        }
        if (sale.getIsPosted()) {
            logger.error("cannot delete posted sale, sale with id: " + id + " is posted");
            return ResponseEntity.badRequest().body(new ObjectWithMessageResponse(new MessageResponse("Can't delete posted sale"), null));
        }

        sale.setDeleted(true);
        productService.increaseQuantities(sale.getSaleItems());
        saleRepository.save(sale);
        logger.info("Sale with id: " + id + "deleted");
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
        productService.decreaseQuantities(sale.getSaleItems());
        saleRepository.save(sale);
        logger.info("Sale with id: " + id + "restored");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sale restored"),
                        SaleMapper.toSaleInfoResponse(sale)
                )
        );
    }

    @Override
    public SalePoint getSalePointBySaleId(Long saleId) {
        if (saleId == null) {
            logger.error("SaleId is null");
            return null;
        }
        Optional<SalePoint> salePointOptional = saleRepository.findSalePointBySaleId(saleId);
        if (salePointOptional.isEmpty()) {
            logger.error("Sale point not found");
            return null;
        }
        return salePointOptional.get();
    }

    @Override
    public Page getAllSalesByDeleted(Pageable pageable, Boolean deleted, LocalDate startDate, LocalDate endDate, Boolean isPaid, Boolean isPosted, Boolean isInvoiced) {
        if (deleted == null) {
            logger.error("Deleted parameter is null");
            return Page.empty();
        }

        Specification<Sale> specification = createBaseSpecification(startDate, endDate, isPaid, isPosted, isInvoiced)
                .and((root, query, cb) -> cb.equal(root.get("deleted"), deleted));
        Page<Sale> salesPage = saleRepository.findAll(specification, pageable);
        Page<SaleInfoResponse> sales = salesPage.map(new Function<Sale, SaleInfoResponse>() {
            @Override
            public SaleInfoResponse apply(Sale sale) {
                return SaleMapper.toSaleInfoResponse(sale);
            }

        });
        return sales;
    }

    @Override
    public Page getSalesBySessionIdAndDeleted(Pageable pageable, Long sessionId, Boolean deleted, LocalDate startDate, LocalDate endDate, Boolean isPaid, Boolean isPosted, Boolean isInvoiced) {
        if (sessionId == null || deleted == null) {
            logger.error("SessionId or Deleted parameter is null");
            return Page.empty();
        }

        if (sessionService.getSessionById(sessionId) == null) {
            logger.error("Session not found");
            return Page.empty();
        }

        Specification<Sale> specification = createBaseSpecification(startDate, endDate, isPaid, isPosted, isInvoiced)
                .and((root, query, cb) -> cb.equal(root.get("session").get("id"), sessionId))
                .and((root, query, cb) -> cb.equal(root.get("deleted"), deleted));

        Page<Sale> salesPages = saleRepository.findAll(specification, pageable);
        Page<SaleInfoResponse> sales = salesPages.map(new Function<Sale, SaleInfoResponse>() {
            @Override
            public SaleInfoResponse apply(Sale sale) {
                return SaleMapper.toSaleInfoResponse(sale);
            }

        });

        return sales;
    }

    @Override
    public Page getSalesBySalePointId(Pageable pageable, Long salePointId, LocalDate startDate, LocalDate endDate, Boolean isPaid, Boolean isPosted, Boolean isInvoiced) {
        if (salePointId == null) {
            logger.error("SalePointId is null");
            return Page.empty();
        }

        if (salePointService.getSalePointById(salePointId) == null) {
            logger.error("Sale point not found");
            return Page.empty();
        }

        Specification<Sale> specification = createBaseSpecification(startDate, endDate, isPaid, isPosted, isInvoiced)
                .and((root, query, cb) -> cb.equal(root.get("session").get("salePoint").get("id"), salePointId));

        Page<Sale> salesPages = saleRepository.findAll(specification, pageable);
        Page<SaleInfoResponse> sales = salesPages.map(new Function<Sale, SaleInfoResponse>() {
            @Override
            public SaleInfoResponse apply(Sale sale) {
                return SaleMapper.toSaleInfoResponse(sale);
            }

        });

        return sales;
    }

    private Specification<Sale> createBaseSpecification(LocalDate startDate, LocalDate endDate, Boolean isPaid, Boolean isPosted, Boolean isInvoiced) {
        Specification<Sale> specification = Specification.where(null);

        specification = specification.and(SaleSpecification.hasDateBetween(startDate, endDate));
        specification = specification.and(SaleSpecification.isPaid(isPaid));
        specification = specification.and(SaleSpecification.isPosted(isPosted));
        specification = specification.and(SaleSpecification.isInvoiced(isInvoiced));

        return specification;
    }


}
