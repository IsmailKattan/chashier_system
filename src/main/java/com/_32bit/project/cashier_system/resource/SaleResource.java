package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.sale.CreateSaleRequest;
import com._32bit.project.cashier_system.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
