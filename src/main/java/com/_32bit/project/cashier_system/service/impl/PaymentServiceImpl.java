package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.PaymentRepository;
import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.domains.Payment;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.enums.PaymentMethod;
import com._32bit.project.cashier_system.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    @Override
    public List<Payment> getPaymentsFromPaymentOfSaleDto(List<PaymentOfSaleDto> request, Sale sale) {
        if (request == null) {
            return new ArrayList<>();
        }

        List<Payment> payments = new ArrayList<>();
        for (PaymentOfSaleDto paymentDto: request) {
            PaymentMethod.valueOf(paymentDto.getPaymentMethod().toLowerCase());
            Payment payment = Payment.builder()
                    .paymentMethod(PaymentMethod.valueOf(paymentDto.getPaymentMethod().toLowerCase()))
                    .sale(sale)
                    .deleted(false)
                    .amount(paymentDto.getAmount() == null ? 0 : paymentDto.getAmount())
                    .build();

            payments.add(payment);
        }
        paymentRepository.saveAll(payments);
        return payments;
    }
}
