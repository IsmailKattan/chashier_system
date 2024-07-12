package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.enums.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long>, JpaSpecificationExecutor<Sale> {

    Optional<Sale> findByIdAndDeleted(Long id, Boolean deleted);

    List<Sale> findAllByDeleted(Boolean deleted);

    Page<Sale> findAllByDeleted(Pageable pageable,Boolean deleted);

    @Query("SELECT s FROM Sale s WHERE s.session.salePoint.id = :salePointId")
    List<Sale> findSalesBySalePointId(@Param("salePointId") Long salePointId);

    @Query("SELECT s FROM Sale s WHERE s.session.salePoint.id = :salePointId")
    Page<Sale> findSalesBySalePointId(Pageable pageable,@Param("salePointId") Long salePointId);
    @Query("SELECT s.session.salePoint FROM Sale s WHERE s.id = :saleId")
    Optional<SalePoint> findSalePointBySaleId(@Param("saleId") Long saleId);

    Page<Sale> findBySessionIdAndDeleted(Pageable pageable, Long sessionId,Boolean deleted);

    List<Sale> findBySessionIdAndDeleted(Long sessionId, Boolean deleted);
}
