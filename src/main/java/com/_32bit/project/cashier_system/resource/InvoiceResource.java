package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.invoice.InvoiceEmailRequest;
import com._32bit.project.cashier_system.DTO.invoice.InvoiceInfoDto;
import com._32bit.project.cashier_system.pdf.PDFGenerator;
import com._32bit.project.cashier_system.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceResource {

    private final InvoiceService invoiceService;

    @GetMapping("/test")
    public String test() {
        return "Invoice Resource works!";
    }

    @GetMapping("/invoices")
    public ResponseEntity<?> getInvoices() {
        return invoiceService.getInvoices();
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    @GetMapping("/sale-invoice/{saleId}")
    public ResponseEntity<?> getInvoiceBySaleId(@PathVariable Long saleId) {
        return invoiceService.getInvoiceBySaleId(saleId);
    }

    @GetMapping("/sale-point-invoices/{salePointId}")
    public ResponseEntity<?> getInvoicesBySalePointId(@PathVariable Long salePointId) {
        return invoiceService.getInvoicesBySalePointId(salePointId);
    }

    @GetMapping("/team-member-invoices/{teamMemberId}")
    public ResponseEntity<?> getInvoicesByTeamMemberId(@PathVariable Long teamMemberId) {
        return invoiceService.getInvoicesByTeamMemberId(teamMemberId);
    }

    @GetMapping("/generate-invoice/{invoiceId}")
    public ResponseEntity<?> generateInvoice(@PathVariable Long invoiceId) {
        return invoiceService.generateInvoice(invoiceId);
    }

}
