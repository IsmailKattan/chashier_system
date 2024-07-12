package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {

    Optional<TeamMember> findByIdAndDeleted(Long id, Boolean deleted);

    Optional<TeamMember> findByUsername(String username);

    Optional<TeamMember> findByUsernameAndDeleted(String username, Boolean deleted);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);

    List<TeamMember> findAllByDeleted(Boolean deleted);
}
