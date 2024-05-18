package com.fpt.adminservice.auth.controller;


import com.fpt.adminservice.auth.dto.AuthenticationRequest;
import com.fpt.adminservice.auth.dto.AuthenticationResponse;
import com.fpt.adminservice.admin.dto.UserCreateRequest;
import com.fpt.adminservice.admin.dto.UserDto;
import com.fpt.adminservice.auth.service.AuthenticationService;
import com.fpt.adminservice.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }
  @PostMapping("/register")
  public ResponseEntity<UserDto> register(
          @RequestBody UserCreateRequest request
  ) {
    return ResponseEntity.ok(userService.createUser(request));
  }
}
