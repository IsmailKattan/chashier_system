package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.domains.UserCredential;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserCredential loadUserById(Long userId);

}
