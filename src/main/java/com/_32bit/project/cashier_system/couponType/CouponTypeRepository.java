package com._32bit.project.cashier_system.couponType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponTypeRepository extends JpaRepository<CouponType,Long> {
    Optional<CouponType> findByIdAndDeleted(Long id, Boolean deleted);
    Optional<CouponType> findByNameLike(String name);
    Optional<CouponType> findByName(String name);
    Optional<CouponType> findByNameLikeAndDeleted(String name, Boolean deleted);
    Optional<CouponType> findByNameAndDeleted(String name, Boolean deleted);
    Optional<CouponType> findByDeleted(Boolean deleted);

}
