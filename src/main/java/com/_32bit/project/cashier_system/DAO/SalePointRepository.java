package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.SalePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalePointRepository extends JpaRepository<SalePoint,Long> {
    Optional<SalePoint> findByIdAndDeleted(Long id, Boolean deleted);
}
