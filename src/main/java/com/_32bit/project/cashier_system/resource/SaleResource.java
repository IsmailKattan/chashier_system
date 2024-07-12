package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.sale.CreateSaleRequest;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleResource {

    private final SaleService saleService;


    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/sales")
    public ResponseEntity<?> getSales(){
        return saleService.getSaleListByDeleted(false);
    }

    @GetMapping("/deleted-sales")
    public ResponseEntity<?> getDeletedSales(){
        return saleService.getSaleListByDeleted(true);
    }
    @GetMapping("/sale/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id){
        return saleService.getSaleByIdAndDeleted(id,false);
    }

    @GetMapping("/deleted-sale/{id}")
    public ResponseEntity<?> getDeletedSaleById(@PathVariable Long id){
        return saleService.getSaleByIdAndDeleted(id,true);
    }

    @GetMapping("/sale-point-sales/{id}")
    public ResponseEntity<?> getSalePointSales(@PathVariable Long id){
        return saleService.getSalePointSales(id);
    }

    @GetMapping("/session-sales/{id}")
    public ResponseEntity<?> getSessionSales(@PathVariable Long id){
        return saleService.getSessionSales(id);
    }

    @PostMapping("/create-sale")
    public ResponseEntity<?> createSale(@RequestBody CreateSaleRequest request, HttpServletRequest httpRequest){
        String token = httpRequest.getHeader("Authorization");
        return saleService.createSale(request,token);
    }

    @PostMapping("/payment/{saleId}")
    public ResponseEntity<?> payment(@PathVariable Long saleId, @RequestBody List<PaymentOfSaleDto> request){
        return saleService.payment(saleId,request);
    }

    @PostMapping("/invoice/{saleId}")
    public ResponseEntity<?> invoice(@PathVariable Long saleId){
        return saleService.invoice(saleId);
    }

    @PutMapping("/update-sale/{id}")
    public ResponseEntity<?> updateSale(@PathVariable Long id, @RequestBody CreateSaleRequest request){
        return saleService.updateSale(id,request);
    }

    @DeleteMapping("/delete-sale/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable Long id){
        return saleService.deleteSale(id);
    }

    @PutMapping("/restore-sale/{id}")
    public ResponseEntity<?> restoreSale(@PathVariable Long id){
        return saleService.restoreSale(id);
    }

    @GetMapping("/paged-sales")
    public ResponseEntity<?> getAllSales(
            Pageable pageable,
            @RequestParam(required = false) Boolean deleted,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Boolean isPaid,
            @RequestParam(required = false) Boolean isPosted,
            @RequestParam(required = false) Boolean isInvoiced) {

        var sales = saleService.getAllSalesByDeleted(pageable, deleted, startDate, endDate, isPaid, isPosted, isInvoiced);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/paged-session-sales/{sessionId}")
    public ResponseEntity<Page<Sale>> getSalesBySession(
            @PathVariable Long sessionId,
            Pageable pageable,
            @RequestParam(required = false) Boolean deleted,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Boolean isPaid,
            @RequestParam(required = false) Boolean isPosted,
            @RequestParam(required = false) Boolean isInvoiced) {

        Page<Sale> sales = saleService.getSalesBySessionIdAndDeleted(pageable, sessionId, deleted, startDate, endDate, isPaid, isPosted, isInvoiced);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/paged-sale-point-sales/{salePointId}")
    public ResponseEntity<Page<Sale>> getSalesBySalePoint(
            @PathVariable Long salePointId,
            Pageable pageable,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Boolean isPaid,
            @RequestParam(required = false) Boolean isPosted,
            @RequestParam(required = false) Boolean isInvoiced) {

        Page<Sale> sales = saleService.getSalesBySalePointId(pageable, salePointId, startDate, endDate, isPaid, isPosted, isInvoiced);
        return ResponseEntity.ok(sales);
    }

}
