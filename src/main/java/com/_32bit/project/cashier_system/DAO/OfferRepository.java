package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
    Optional<Offer> findByIdAndDeleted(Long id, Boolean deleted);

    List<Offer> findAllByDeleted(Boolean deleted);

    List<Offer> findByNameContainsAndDeleted(String name, Boolean deleted);

    List<Offer> findByDescriptionContinsAndDeleted(String description, Boolean deleted);

    List<Offer> findByStartDateBetweenAndDeleted(String startDate, String endDate, Boolean deleted);
}
