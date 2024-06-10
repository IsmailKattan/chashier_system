package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
    Optional<Offer> findByIdAndDeleted(Long id, Boolean deleted);
    Optional<Offer> findByNameLikeAndDeleted(String name, Boolean deleted);
    Optional<Offer> findByDescriptionLikeAndDeleted(String Description, Boolean deleted);
    Optional<Offer> findByInsertionDateBeforeAndDeleted(Date insertionDate, Boolean deleted);
    Optional<Offer> findByInsertionDateAfterAndDeleted(Date insertionDate, Boolean deleted);
    Optional<Offer> findByStartDateBeforeAndDeleted(Date startDate, Boolean deleted);
    Optional<Offer> findByStartDateAfterAndDeleted(Date startDate, Boolean deleted);
    Optional<Offer> findByEndDateBeforeAndDeleted(Date endDate, Boolean deleted);
    Optional<Offer> findByEndDateAfterAndDeleted(Date endDate, Boolean deleted);
    Optional<Offer> findByGetCountAndPayForAndDeleted(Integer getCount,Integer payFor, Boolean deleted);
    Optional<Offer> findByDeleted(Boolean deleted);
}
