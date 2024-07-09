package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.domains.Payment;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentMapper {
    public static List<PaymentOfSaleDto> toPaymentOfSaleDtoList(List<Payment> payments) {
        return payments.stream()
                .map(PaymentMapper::toPaymentOfSaleDto)
                .collect(Collectors.toList());
    }

    private static PaymentOfSaleDto toPaymentOfSaleDto(Payment payment) {
        return PaymentOfSaleDto.builder()
                .paymentMethod(payment.getPaymentMethod().name())
                .amount(payment.getAmount())
                .build();
    }
}
