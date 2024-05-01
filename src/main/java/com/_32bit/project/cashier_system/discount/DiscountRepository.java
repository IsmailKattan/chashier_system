package com._32bit.project.cashier_system.discount;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    Optional<Discount> findByIdAndDeleted(Long id, Boolean deleted);
    Optional<Discount> findByName(String name);
    Optional<Discount> findByNameLike(String name);
    Optional<Discount> findByStartDateBefore(Date startDate);
    Optional<Discount> findByStartDateAfter(Date startDate);
    Optional<Discount> findByEndDateBefore(Date endDate);
    Optional<Discount> findByEndDateAfter(Date endDate);
    Optional<Discount> findByNameAndDeleted(String name, Boolean deleted);
    Optional<Discount> findByNameLikeAndDeleted(String name, Boolean deleted);
    Optional<Discount> findByStartDateBeforeAndDeleted(Date startDate, Boolean deleted);
    Optional<Discount> findByStartDateAfterAndDeleted(Date startDate, Boolean deleted);
    Optional<Discount> findByEndDateBeforeAndDeleted(Date endDate, Boolean deleted);
    Optional<Discount> findByEndDateAfterAndDeleted(Date endDate, Boolean deleted);
    Optional<Discount> findByDeleted(Boolean deleted);
}
