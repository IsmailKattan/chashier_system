package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
