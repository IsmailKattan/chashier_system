package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem,Long> {

    Optional<SaleItem> findByIdAndDeleted(Long id, Boolean deleted);

    Optional<SaleItem> findByDeleted(Boolean deleted);
}
