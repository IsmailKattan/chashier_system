package com._32bit.project.cashier_system.productGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup,Long> {

    Optional<ProductGroup> findByIdAndDeleted(Long id, Boolean deleted);
    Optional<ProductGroup> findByName(String name);
    Optional<ProductGroup> findByNameAndDeleted(String name, Boolean deleted);
    Optional<ProductGroup> findByDeleted(Boolean deleted);

}
