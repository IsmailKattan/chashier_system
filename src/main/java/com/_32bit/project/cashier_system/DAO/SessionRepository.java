package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    Optional<Session> findByIdAndDeleted(Long id, Boolean Deleted );

    List<Session> findAllByDeleted(Boolean deleted);

    Optional<Session> findByIdAndSalePointId(Long sessionId, Long salePointId);
}
