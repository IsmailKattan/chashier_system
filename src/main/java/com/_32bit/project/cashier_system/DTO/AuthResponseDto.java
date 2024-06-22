package com._32bit.project.cashier_system.DTO;


import com._32bit.project.cashier_system.DTO.enums.AuthStatus;

public record AuthResponseDto(String Token, AuthStatus authStatus) {
}
