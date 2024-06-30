package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SalePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalePointRepository extends JpaRepository<SalePoint,Long> {

    List<SalePoint>  findAllByDeleted(Boolean deleted);
    Optional<SalePoint> findByIdAndDeleted(Long id, Boolean deleted);

    List<SalePoint> findByAddressContainsAndDeleted(String address, Boolean deleted);

    List<SalePoint> findByNameContainsAndDeleted(String salePointName,Boolean deleted);
}
