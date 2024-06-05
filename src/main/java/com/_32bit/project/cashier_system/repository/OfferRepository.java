package com._32bit.project.cashier_system.repository;


import com._32bit.project.cashier_system.domains.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    Optional<Offer> findByIdAndDeleted(Long id, Boolean deleted);
    Optional<Offer> findByDescriptionLike(String Description);
    Optional<Offer> findByStartDateBefore(Date startDate);
    Optional<Offer> findByStartDateAfter(Date startDate);
    Optional<Offer> findByEndDateBefore(Date endDate);
    Optional<Offer> findByEndDateAfter(Date endDate);
    Optional<Offer> findByOfferLimitGreaterThan(Double offerLimit);
    Optional<Offer> findByOfferLimitLessThan(Double offerLimit);
    Optional<Offer> findByDescriptionLikeAndDeleted(String Description, Boolean deleted);
    Optional<Offer> findByStartDateBeforeAndDeleted(Date startDate, Boolean deleted);
    Optional<Offer> findByStartDateAfterAndDeleted(Date startDate, Boolean deleted);
    Optional<Offer> findByEndDateBeforeAndDeleted(Date endDate, Boolean deleted);
    Optional<Offer> findByEndDateAfterAndDeleted(Date endDate, Boolean deleted);
    Optional<Offer> findByOfferLimitGreaterThanAndDeleted(Double offerLimit, Boolean deleted);
    Optional<Offer> findByOfferLimitLessThanAndDeleted(Double offerLimit, Boolean deleted);

    Optional<Offer> findByDeleted(Boolean deleted);


}
