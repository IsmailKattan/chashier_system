package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByIdAndDeleted(Long id, Boolean deleted);
    Optional<Product> findByNameAndDeleted(String name, Boolean deleted);
    Optional<Product> findByBrandAndDeleted(String brand, Boolean deleted);
    Optional<Product> findByNameOrBrandAndDeleted(String name,String brande, Boolean deleted);
    Boolean existsByNameAndBrandAndDeleted(String name,String brand, Boolean deleted);
    Optional<Product> findByQuantityGreaterThanAndDeleted(Double quantity, Boolean deleted);
    Optional<Product> findByPriceGreaterThanAndDeleted(Double price, Boolean deleted);
    Optional<Product> findByQuantityLessThanAndDeleted(Double quantity, Boolean deleted);
    Optional<Product> findByPriceLessThanAndDeleted(Double price, Boolean deleted);
    Boolean existsByNameAndBrand(String name,String brand);
    Optional<Product> findByIsDiscounted(Boolean isDiscounted);
    Optional<Product> findByDiscountedPriceGreaterThanAndDeleted(Double price, Boolean deleted);
    Optional<Product> findByDiscountedPriceLessThanAndDeleted(Double price, Boolean deleted);
    Optional<Product> findByDeleted(Boolean deleted);
}
