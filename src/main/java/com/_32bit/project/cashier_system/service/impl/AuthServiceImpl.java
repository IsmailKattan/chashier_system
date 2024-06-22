package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.UserCredentialRepository;
import com._32bit.project.cashier_system.DTO.AuthRequestDto;
import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.UserCredential;
import com._32bit.project.cashier_system.configuration.util.JwtUtils;
import com._32bit.project.cashier_system.domains.enums.ERole;
import com._32bit.project.cashier_system.service.AuthService;
import io.jsonwebtoken.security.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public String login(String username, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(username,password);

        var authenticate = authenticationManager.authenticate(authToken);

        return JwtUtils.generateToken(((UserDetails)(authenticate.getPrincipal())).getUsername());
    }

    @Override
    public String signup(String username, String password, List<String> roles) {
        if(userCredentialRepository.existsByUsername(username)) {
            throw new RuntimeException("user already exists");
        }

        var encodedPassword = passwordEncoder.encode(password);
        List<Role> roleList = new ArrayList<>();
        for(String role : roles) {
            Role roleIem = new Role();
            roleList.add(Role.builder().name(ERole.valueOf(role)).build());
        }

        UserCredential build = UserCredential.builder()
                .username(username)
                .password(encodedPassword)
                .roles(roleList)
                .build();

        userCredentialRepository.save(build);

        return JwtUtils.generateToken(username);
    }
}
