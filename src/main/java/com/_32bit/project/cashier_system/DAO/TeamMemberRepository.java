package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {

    Optional<TeamMember> findByIdAndDeleted(Long id, Boolean deleted);

    Optional<TeamMember> findByDeleted(Boolean deleted);

    Optional<TeamMember> findByFirstname(String firstname);

    Optional<TeamMember> findByLastname(String lastname);

    Optional<TeamMember> findByUserCredentialAndDeleted(UserCredential userCredential, Boolean deleted);

    Optional<List<TeamMember>> findByFirstnameOrLastnameAndDeleted(String firstname, String lastname, Boolean deleted);

}
