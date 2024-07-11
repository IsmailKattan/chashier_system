package com._32bit.project.cashier_system.security.jwt;

import com._32bit.project.cashier_system.security.service.UserDetailsImpl;
import com._32bit.project.cashier_system.service.impl.AuthServiceImpl;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${32_bit.app.jwtSecret}")
    private String jwtSecret;

    @Value("${32_bit.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private final static Logger logger = LogManager.getLogger(JwtUtils.class);


    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        logger.info("Generating JWT token for user: " + userPrincipal.getUsername());

        try {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

            String token = Jwts.builder()
                    .setSubject(userPrincipal.getUsername())
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();

            logger.info("JWT token generated successfully");
            return token;
        } catch (Exception e) {
            logger.error("Error generating JWT token", e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Error: Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Error: Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Error: JWT token expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Error: JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}