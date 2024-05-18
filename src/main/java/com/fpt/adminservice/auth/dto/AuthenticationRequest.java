package com.fpt.adminservice.auth.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {

  private String email;
  private String password;

  public AuthenticationRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

}
