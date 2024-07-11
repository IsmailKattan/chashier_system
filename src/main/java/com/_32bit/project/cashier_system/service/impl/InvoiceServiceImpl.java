package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.InvoiceRepository;
import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.invoice.InvoiceInfoDto;
import com._32bit.project.cashier_system.domains.Invoice;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.mapper.InvoiceMapper;
import com._32bit.project.cashier_system.service.InvoiceService;
import com._32bit.project.cashier_system.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final static Logger logger = LogManager.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;

    @Override
    public ResponseEntity<?> getInvoiceById(Long id) {
        if (id == null) {
            logger.error("Id cannot be null");
            return ResponseEntity.badRequest().body("Id cannot be null");
        }
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isEmpty()) {
            logger.error("Invoice not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectWithMessageResponse(new MessageResponse("Invoice not found"),null));
        }
        InvoiceInfoDto invoiceInfoDto = InvoiceMapper.toInvoiceInfoDto(invoiceOptional.get());
        logger.info("Invoice found");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Invoice found"),
                        invoiceInfoDto
                )
        );
    }

    @Override
    public ResponseEntity<?> getInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        if (invoices.isEmpty()) {
            logger.error("Invoices not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectWithMessageResponse(new MessageResponse("Invoices not found"),null));
        }
        List<InvoiceInfoDto> invoiceInfoDtos = InvoiceMapper.toInvoiceInfoDtoList(invoices);
        logger.info("Invoices found");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Invoices found"),
                        invoiceInfoDtos
                )
        );
    }

    @Override
    public ResponseEntity<?> getInvoiceBySaleId(Long saleId) {
        if(saleId == null){
            logger.error("SaleId cannot be null");
            return ResponseEntity.badRequest().body("SaleId cannot be null");
        }
        Optional<Invoice> invoiceOptional = invoiceRepository.findBySaleId(saleId);
        if (invoiceOptional.isEmpty()) {
            logger.error("Invoice not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectWithMessageResponse(new MessageResponse("Invoice not found"),null));
        }
        InvoiceInfoDto invoiceInfoDto = InvoiceMapper.toInvoiceInfoDto(invoiceOptional.get());
        logger.info("Invoice found");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Invoice found"),
                        invoiceInfoDto
                )
        );
    }

    @Override
    public ResponseEntity<?> getInvoicesBySalePointId(Long salePointId) {
        if (salePointId == null) {
            logger.error("SalePointId cannot be null");
            return ResponseEntity.badRequest().body("SalePointId cannot be null");
        }
        List<Invoice> invoices = invoiceRepository.findBySalePointId(salePointId);
        if (invoices.isEmpty()) {
            logger.error("Invoices not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectWithMessageResponse(new MessageResponse("Invoices not found"),null));
        }
        List<InvoiceInfoDto> invoiceInfoDtos = InvoiceMapper.toInvoiceInfoDtoList(invoices);
        logger.info("Invoices found");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Invoices found"),
                        invoiceInfoDtos
                )
        );

    }

    @Override
    public ResponseEntity<?> getInvoicesByTeamMemberId(Long teamMemberId) {
        if (teamMemberId == null) {
            logger.error("TeamMemberId cannot be null");
            return ResponseEntity.badRequest().body("TeamMemberId cannot be null");
        }
        List<Invoice> invoices = invoiceRepository.findBySoldById(teamMemberId);
        if (invoices.isEmpty()) {
            logger.error("Invoices not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectWithMessageResponse(new MessageResponse("Invoices not found"),null));
        }
        List<InvoiceInfoDto> invoiceInfoDtos = InvoiceMapper.toInvoiceInfoDtoList(invoices);
        logger.info("Invoices found");
        return ResponseEntity.ok(
                new ObjectWithMessageResponse(
                        new MessageResponse("Invoices found"),
                        invoiceInfoDtos
                )
        );
    }

    @Override
    public Invoice creaInvoice(Sale sale, TeamMember teamMember, SalePoint salePoint) {
        Invoice invoice = new Invoice();
        invoice.setSale(sale);
        invoice.setSoldBy(teamMember);
        invoice.setSalePoint(salePoint);
        invoice.setExtractionDate(LocalDate.now());
        invoice.setExtractionTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        invoiceRepository.save(invoice);
        return invoiceRepository.save(invoice);
    }


}
