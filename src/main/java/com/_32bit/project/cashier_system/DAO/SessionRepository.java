package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    Optional<Session> findByIdAndDeleted(Long id, Boolean Deleted );
}
