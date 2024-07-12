package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.sale.CreateSaleRequest;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SalePoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface SaleService {

    ResponseEntity<?> getSaleListByDeleted(Boolean deleted);

    ResponseEntity<?> getSaleByIdAndDeleted(Long id, Boolean deleted);

    Sale getSaleObjectByIdAndDeleted(Long id,Boolean deleted);

    ResponseEntity<?> getSalePointSales(Long SalePointId);

    ResponseEntity<?> getSessionSales(Long id);

    List<Sale> getSalesBySalePointId(Long salePointId);

    ResponseEntity<?> createSale(CreateSaleRequest request,String token);

    void postSessionSales(Long sessionId);

    ResponseEntity<?> payment(Long saleId, List<PaymentOfSaleDto> request);

    ResponseEntity<?> invoice(Long saleId);

    ResponseEntity<?> updateSale(Long id, CreateSaleRequest request);

    ResponseEntity<?> deleteSale(Long id);

    ResponseEntity<?> restoreSale(Long id);

    SalePoint getSalePointBySaleId(Long saleId);

    Page<Sale> getAllSalesByDeleted(Pageable pageable, Boolean deleted, LocalDate startDate, LocalDate endDate, Boolean isPaid, Boolean isPosted, Boolean isInvoiced);

    Page<Sale> getSalesBySessionIdAndDeleted(Pageable pageable, Long sessionId, Boolean deleted, LocalDate startDate, LocalDate endDate, Boolean isPaid, Boolean isPosted, Boolean isInvoiced);

    Page<Sale> getSalesBySalePointId(Pageable pageable, Long salePointId, LocalDate startDate, LocalDate endDate, Boolean isPaid, Boolean isPosted, Boolean isInvoiced);
}
