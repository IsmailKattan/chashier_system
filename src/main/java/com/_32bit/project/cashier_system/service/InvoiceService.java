package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.invoice.InvoiceInfoDto;
import com._32bit.project.cashier_system.domains.Invoice;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.TeamMember;
import org.springframework.http.ResponseEntity;

public interface InvoiceService {

    ResponseEntity<?> getInvoiceById(Long id);
    ResponseEntity<?> getInvoices();
    ResponseEntity<?> getInvoiceBySaleId(Long saleId);
    ResponseEntity<?> getInvoicesBySalePointId(Long salePointId);
    ResponseEntity<?> getInvoicesByTeamMemberId(Long teamMemberId);
    Invoice creaInvoice(Sale sale, TeamMember teamMember, SalePoint salePoint);

    ResponseEntity<?> generateInvoice(Long invoiceId);

    InvoiceInfoDto getInvoiceObjectById(Long invoiceId);
}
