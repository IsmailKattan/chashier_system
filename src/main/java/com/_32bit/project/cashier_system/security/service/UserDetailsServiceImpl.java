package com._32bit.project.cashier_system.security.service;


import com._32bit.project.cashier_system.DAO.UserCredentialRepository;
import com._32bit.project.cashier_system.domains.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserCredentialRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}