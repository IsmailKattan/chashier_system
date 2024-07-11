package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.sale.CreateSaleRequest;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SalePoint;
import org.springframework.http.ResponseEntity;

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


}
