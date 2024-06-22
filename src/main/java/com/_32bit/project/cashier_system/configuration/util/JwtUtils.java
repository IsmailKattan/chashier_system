package com._32bit.project.cashier_system.configuration.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class JwtUtils {

    // based on coding streams

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public static boolean validateToken(String jwtToken) {
        return parseToken(jwtToken).isPresent();
    }

    private static Optional<Claims> parseToken(String jwtToken) {
        var jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        try {
            return Optional.of(jwtParser.parseSignedClaims(jwtToken)
                    .getPayload());
        } catch (JwtException e) {
            log.error("Jwt exception occurred");
        }
        return Optional.empty();
    }

    public static Optional<String> getUsernameFromToken(String jwtToken) {
        var claimsOptional = parseToken(jwtToken);

        return claimsOptional.map(Claims::getSubject);
    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*5))
                .compact();
    }
}
