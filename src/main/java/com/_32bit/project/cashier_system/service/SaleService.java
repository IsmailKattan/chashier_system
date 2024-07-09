package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.sale.CreateSaleRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SaleService {

    ResponseEntity<?> getSaleListByDeleted(Boolean deleted);

    ResponseEntity<?> getSaleByIdAndDeleted(Long id, Boolean deleted);

    ResponseEntity<?> getSalePointSalesByDeleted(Long SalePointId, Boolean deleted);

    ResponseEntity<?> getSessionSalesByDeleted(Long id, Boolean deleted);

    ResponseEntity<?> createSale(CreateSaleRequest request,String token);

    ResponseEntity<?> payment(Long saleId, List<PaymentOfSaleDto> request);

    ResponseEntity<?> invoice(Long saleId);

    ResponseEntity<?> updateSale(Long id, CreateSaleRequest request);

    ResponseEntity<?> deleteSale(Long id);

    ResponseEntity<?> restoreSale(Long id);


}
