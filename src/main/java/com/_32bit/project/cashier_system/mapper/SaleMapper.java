package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.sale.CreateSaleRequest;
import com._32bit.project.cashier_system.DTO.sale.SaleInfoResponse;
import com._32bit.project.cashier_system.DTO.saleItem.ItemOfSaleDto;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SaleItem;
import com._32bit.project.cashier_system.domains.Session;
import com._32bit.project.cashier_system.domains.TeamMember;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SaleMapper {

    public static SaleInfoResponse toSaleInfoResponse(Sale sale) {
        return SaleInfoResponse.builder()
                .salePointId(sale.getSession().getSalePoint().getId() != null ? sale.getSession().getSalePoint().getId() : null)
                .sessionId(sale.getSession().getId() != null ? sale.getSession().getId() : null)
                .id(sale.getId() != null ? sale.getId() : null)
                .saleDate(sale.getSaleDate() != null ? sale.getSaleDate() : null)
                .saleTime(sale.getSaleTime() != null ? sale.getSaleTime() : null)
                .items(SaleItemMapper.toItemOfSaleDtoList(sale.getSaleItems()))
                .payments(PaymentMapper.toPaymentOfSaleDtoList(sale.getPayments()) != null ? PaymentMapper.toPaymentOfSaleDtoList(sale.getPayments()) : null)
                .total(sale.getTotal() != null ? sale.getTotal() : null)
                .isPaid(sale.getIsPaid() != null ? sale.getIsPaid() : null)
                .isPosted(sale.getIsPosted() != null ? sale.getIsPosted() : null)
                .isInvoiced(sale.getIsInvoiced() != null ? sale.getIsInvoiced() : null)
                .soldById(sale.getSoldBy().getUsername() != null ? sale.getSoldBy().getUsername() : null)
                .build();
    }

    public static List<SaleInfoResponse> toSaleInfoResponseList(List<Sale> sales) {
        return sales.stream()
                .map(SaleMapper::toSaleInfoResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    public static Sale createSaleRequestToSale(CreateSaleRequest request, TeamMember teamMember, Session session) {
        return Sale.builder()
                .saleDate(LocalDate.now())
                .saleTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS))
                .isPaid(false)
                .isPosted(false)
                .isInvoiced(false)
                .deleted(false)
                .soldBy(teamMember)
                .session(session)
                .build();
    }

}
