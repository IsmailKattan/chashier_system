package com._32bit.project.cashier_system.repository;

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
    Optional<Product> findByName(String name);
    Optional<Product> findByBrand(String brand);
    Optional<Product> findByNameOrBrand(String name,String brande);
    Boolean existsByNameAndBrand(String name,String brand);
    Optional<Product> findByQuantityGreaterThan(Double quantity);
    Optional<Product> findByPriceGreaterThan(Double price);
    Optional<Product> findByQuantityLessThan(Double quantity);
    Optional<Product> findByPriceLessThan(Double price);

    Optional<Product> findByDeleted(Boolean deleted);
}
