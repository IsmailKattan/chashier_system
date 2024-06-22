package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.UserCredentialRepository;
import com._32bit.project.cashier_system.domains.UserCredential;
import com._32bit.project.cashier_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserCredential loadUserById(Long userId) {
        return userCredentialRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"unauthorized"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userCredentialRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("email not found!"));

        return new User(user.getEmail(),user.getPassword(),user.getAuthorities());

    }
}
