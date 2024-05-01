package com._32bit.project.cashier_system.coupon;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Optional<Coupon> findByIdAndDeleted(Long id, Boolean deleted);
    Optional<Coupon> findByNameLike(String name);
    Optional<Coupon> findByName(String name);
    Optional<Coupon> findByStartDateBefore(Date startDate);
    Optional<Coupon> findByStartDateAfter(Date startDate);
    Optional<Coupon> findByEndDateBefore(Date endDate);
    Optional<Coupon> findByEndDateAfter(Date endDate);
    Optional<Coupon> findByNameLikeAndDeleted(String name, Boolean deleted);
    Optional<Coupon> findByNameAndDeleted(String name, Boolean deleted);
    Optional<Coupon> findByStartDateBeforeAndDeleted(Date startDate, Boolean deleted);
    Optional<Coupon> findByStartDateAfterAndDeleted(Date startDate, Boolean deleted);
    Optional<Coupon> findByEndDateBeforeAndDeleted(Date endDate, Boolean deleted);
    Optional<Coupon> findByEndDateAfterAndDeleted(Date endDate, Boolean deleted);
    Optional<Coupon> findByDeleted(Boolean deleted);
}
