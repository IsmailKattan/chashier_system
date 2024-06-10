package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByIdAndDeleted(Long Id,Boolean deleted);
    Optional<Role> findByNameAndDeleted(String name, Boolean deleted);

    Optional<Role> findByDeleted(Boolean deleted);

}
