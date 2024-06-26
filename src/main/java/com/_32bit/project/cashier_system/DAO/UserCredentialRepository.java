package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential,Long> {

    Optional<UserCredential> findByIdAndDeleted(Long id, boolean b);

    Optional<UserCredential> findByEmail(String email);

    Optional<UserCredential> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserCredential> findByPhoneNumber(String phoneNumber);
}
