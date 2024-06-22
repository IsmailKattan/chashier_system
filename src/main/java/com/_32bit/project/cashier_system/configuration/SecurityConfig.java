package com._32bit.project.cashier_system.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // based on coding streams

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationConfig authenticationConfig;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public org.springframework.security.web.SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/manager/**").hasRole("MANAGER");
                    auth.requestMatchers("/cashier/**").hasRole("CASHIER");
                    auth.requestMatchers("/admin/login","/admin/signup","/api/secret").permitAll();
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(exConfig ->exConfig.authenticationEntryPoint(authenticationEntryPoint))
//                .logout((logout) -> logout.logoutUrl("/logout")
//                        .logoutSuccessUrl("/logout-success")
//                        .permitAll())
//                .formLogin(l -> l.defaultSuccessUrl("/hi-user"))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .httpBasic(Customizer.withDefaults())
                .build();
    }



    @Autowired
    public void securityFilterChain(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("32-bit-admin")
                .password(authenticationConfig.bCryptPasswordEncoder().encode("32-bit"))
                .roles("ADMIN", "MANAGER", "CASHIER")
                .and()
                .withUser("32-bit-manager")
                .password(authenticationConfig.bCryptPasswordEncoder().encode("32-bit"))
                .roles("MANAGER", "CASHIER")
                .and()
                .withUser("32-bit-cashier")
                .password(authenticationConfig.bCryptPasswordEncoder().encode("32-bit"))
                .roles("CASHIER");
    }

}
