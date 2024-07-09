package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.domains.Payment;
import com._32bit.project.cashier_system.domains.Sale;

import java.util.List;

public interface PaymentService {


    List<Payment> getPaymentsFromPaymentOfSaleDto(List<PaymentOfSaleDto> request, Sale sale);
}
