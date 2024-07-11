package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


    Optional<Invoice> findBySaleId(Long saleId);

    List<Invoice> findBySalePointId(Long salePointId);

    List<Invoice> findBySoldById(Long teamMemberId);
}
