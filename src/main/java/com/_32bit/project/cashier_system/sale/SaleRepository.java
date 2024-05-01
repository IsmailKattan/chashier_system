package com._32bit.project.cashier_system.sale;

import com._32bit.project.cashier_system.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {

    Optional<Sale> findByIdAndDeleted(Long id, Boolean deleted);
    Optional<Sale> findBySaleDate(Date saleDate);
    Optional<Sale> findBySaleTime(Time saleTime);
    Optional<Sale> findBySaleDateAndSaleTime(Date saleDate,Time saleTime);
    Optional<Sale> findByPaymentType(PaymentType paymentType);
    Optional<Sale> findBySalesValueGreaterThan(Double salesValue);
    Optional<Sale> findBySaleDateAndDeleted(Date saleDate, Boolean deleted);
    Optional<Sale> findBySaleTimeAndDeleted(Time saleTime, Boolean deleted);
    Optional<Sale> findBySaleDateAndSaleTimeAndDeleted(Date saleDate,Time saleTime, Boolean deleted);
    Optional<Sale> findByPaymentTypeAndDeleted(PaymentType paymentType, Boolean deleted);
    Optional<Sale> findBySalesValueGreaterThanAndDeleted(Double salesValue, Boolean deleted);
    Optional<Sale> findByDeleted(Boolean deleted);
}
