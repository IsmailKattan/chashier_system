package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
