package com._32bit.project.cashier_system.configuration;

import com._32bit.project.cashier_system.configuration.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // based on coding streams

    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // gets the token form the request
        var jwtTokenOptional = getTokenFromRequest(request);
        // if there is a value in the token
        jwtTokenOptional.ifPresent(jwtToken -> {
            // checks if there is claims in token
            if (JwtUtils.validateToken(jwtToken)) {
                // gets from token claim object and gets username from this object
                var usernameOptional = JwtUtils.getUsernameFromToken(jwtToken);
                // if username have value
                usernameOptional.ifPresent(username -> {
                    // gets userDetails which authenticated
                    var userDetails = userDetailsService.loadUserByUsername(username);
                    // produce new token for userDetails
                    var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // sets details
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // sets authentication on context
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                });

            }
        });

        filterChain.doFilter(request, response);

    }


    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        //
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}
