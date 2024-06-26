package com._32bit.project.cashier_system.DAO;

import com._32bit.project.cashier_system.domains.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {


    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.loggedOut = false")
    List<Token> findAllAccessTokensByUser(@Param("userId") Long userId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token> findByRefreshToken(String token);
}
