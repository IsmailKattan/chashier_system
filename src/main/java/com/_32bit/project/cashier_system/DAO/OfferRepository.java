package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
    Optional<Offer> findByIdAndDeleted(Long id, Boolean deleted);

    List<Offer> findAllByDeleted(Boolean deleted);

    List<Offer> findByNameContainsAndDeleted(String name, Boolean deleted);

    List<Offer> findByDescriptionContainsAndDeleted(String description, Boolean deleted);

    List<Offer> findByStartDateBetweenAndDeleted(LocalDate startDate, LocalDate endDate, Boolean deleted);
}
