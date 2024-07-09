package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {

    Optional<Sale> findByIdAndDeleted(Long id, Boolean deleted);

    List<Sale> findAllByDeleted(Boolean deleted);

    @Query("SELECT s FROM Sale s WHERE s.session.salePoint.id = :salePointId")
    List<Sale> findSalesBySalePointId(@Param("salePointId") Long salePointId);

    List<Sale> findBySessionId(Long sessionId);

    List<Sale> findBySessionIdAndDeleted(Long sessionId, Boolean deleted);
}
