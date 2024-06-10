package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {

    Optional<TeamMember> findByIdAndDeleted(Long id, Boolean deleted);
    Optional<TeamMember> findByFirstnameAndLastname(String firstname,String lastname);
    Optional<TeamMember> findByFirstnameOrLastname(String firstname,String lastname);
    Optional<TeamMember> findByUsernameOrEmailOrPhoneNumber(String username,String email,String phoneNumber);
    Optional<TeamMember> findByDeleted(Boolean deleted);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    Optional<TeamMember> findByFirstnameAndLastnameAndDeleted(String firstname,String lastname, Boolean deleted);
    Optional<TeamMember> findByFirstnameOrLastnameAndDeleted(String firstname,String lastname, Boolean deleted);
    Optional<TeamMember> findByUsernameOrEmailOrPhoneNumberAndDeleted(String username,String email,String phoneNumber, Boolean deleted);
    Boolean existsByUsernameAndDeleted(String username, Boolean deleted);
    Boolean existsByEmailAndDeleted(String email, Boolean deleted);
}