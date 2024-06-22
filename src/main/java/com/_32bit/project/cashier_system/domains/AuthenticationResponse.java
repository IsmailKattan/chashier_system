package com._32bit.project.cashier_system.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String AccessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("message")
    private String message;

    public AuthenticationResponse(String accessToken, String refreshToken, String message) {
        AccessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() {
        return message;
    }
}
