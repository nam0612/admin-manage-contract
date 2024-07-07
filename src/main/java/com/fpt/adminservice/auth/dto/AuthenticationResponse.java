package com.fpt.adminservice.auth.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpt.adminservice.auth.model.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("user")
    private User user;
    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
