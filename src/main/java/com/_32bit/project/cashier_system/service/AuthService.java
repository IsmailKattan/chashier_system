package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.AuthRequestDto;

import java.util.List;

public interface AuthService {

    String login(String username, String password);

    String signup(String username, String password, List<String> roles);
}
