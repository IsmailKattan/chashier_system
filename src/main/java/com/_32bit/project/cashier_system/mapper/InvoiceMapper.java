package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.invoice.InvoiceInfoDto;
import com._32bit.project.cashier_system.domains.Invoice;

import java.util.List;

public class InvoiceMapper {
    public static InvoiceInfoDto toInvoiceInfoDto(Invoice invoice) {
        return InvoiceInfoDto.builder()
                .salePointName(invoice.getSalePoint().getName() != null ? invoice.getSalePoint().getName() : null)
                .salePointAddress(invoice.getSalePoint().getAddress() != null ? invoice.getSalePoint().getAddress() : null)
                .saleDate(invoice.getSale().getSaleDate() != null ? invoice.getSale().getSaleDate() : null)
                .saleTime(invoice.getSale().getSaleTime() != null ? invoice.getSale().getSaleTime() : null)
                .saleId(invoice.getSale().getId() != null ? invoice.getSale().getId() : null)
                .teamMemberName(invoice.getSoldBy().getUsername() != null ? invoice.getSoldBy().getUsername() : null)
                .saleItems(SaleItemMapper.toSaleItemInfoResponseDtoList(invoice.getSale().getSaleItems()))
                .total(invoice.getSale().getTotal() != null ? invoice.getSale().getTotal() : null)
                .payments(PaymentMapper.toPaymentOfSaleDtoList(invoice.getSale().getPayments()))
                .id(invoice.getId() != null ? invoice.getId() : null)
                .extractionDate(invoice.getExtractionDate() != null ? invoice.getExtractionDate() : null)
                .extractionTime(invoice.getExtractionTime() != null ? invoice.getExtractionTime() : null)
                .build();

    }

    public static List<InvoiceInfoDto> toInvoiceInfoDtoList(List<Invoice> invoices) {
        return invoices.stream()
                .map(InvoiceMapper::toInvoiceInfoDto)
                .toList();
    }
}
