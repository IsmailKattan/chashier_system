package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {

    Optional<Sale> findByIdAndDeleted(Long id, Boolean deleted);
    Optional<Sale> findBySaleDateBeforeAndDeleted(Date saleDate, Boolean deleted);
    Optional<Sale> findBySaleTimeAfterAndDeleted(Time saleTime, Boolean deleted);
    Optional<Sale> findBySaleDateBetweenAndDeleted(Date startDate,Date endDate,Boolean deleted);
    Optional<Sale> findByTotalGreaterThanAndDeleted(Double Total,Boolean deleted);
    Optional<Sale> findByTotalLessThanAndDeleted(Double Total,Boolean deleted);
    Optional<Sale> findByTotalAndDeleted(Double Total,Boolean deleted);
    Optional<Sale> findBySaleDateAndDeleted(Date saleDate, Boolean deleted);
    Optional<Sale> findBySaleDateAndSaleTimeBetweenAndDeleted(Date saleDate,Time startTime,Time endTime, Boolean deleted);
    Optional<Sale> findByPaymentTypeAndDeleted(PaymentType paymentType, Boolean deleted);
    Optional<Sale> findByIsDiscountedAndDeleted(Boolean isDiscounted,Boolean deleted);
    Optional<Sale> findByDeleted(Boolean deleted);
}
