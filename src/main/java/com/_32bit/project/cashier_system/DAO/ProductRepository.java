package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByIdAndDeleted(Long id, Boolean deleted);

    List<Product> findAllByDeleted(Boolean deleted);

    List<Product> findByNameContainsAndDeleted(String name, Boolean deleted);

    List<Product> findByBrandContainsAndDeleted(String brand, Boolean deleted);

    List<Product> findByCategoryAndDeleted(String category, Boolean deleted);
}
