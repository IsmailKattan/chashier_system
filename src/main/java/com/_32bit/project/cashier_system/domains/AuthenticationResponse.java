package com._32bit.project.cashier_system.domains;

public class AuthenticationResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
