package com.fpt.adminservice.auth.service;



import com.fpt.adminservice.auth.dto.AuthenticationRequest;
import com.fpt.adminservice.auth.dto.AuthenticationResponse;
import com.fpt.adminservice.auth.model.User;
import com.fpt.adminservice.auth.repository.UserRepository;
import com.fpt.adminservice.config.JwtService;
import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public BaseResponse authenticate(AuthenticationRequest request) {
    var userOptional = repository.findByEmail(request.getEmail());
    if (userOptional.isEmpty()) {
      return new BaseResponse(Constants.ResponseCode.NOT_FOUND, "User not found", true, null);
    }
    var user = userOptional.get();
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );


      var jwtToken = jwtService.generateToken(user);
      var refreshToken = jwtService.generateRefreshToken(user);

      AuthenticationResponse authenticationResponse = new AuthenticationResponse();
      authenticationResponse.setAccessToken(jwtToken);
      authenticationResponse.setRefreshToken(refreshToken);
      authenticationResponse.setUser(user);
      return new BaseResponse(Constants.ResponseCode.SUCCESS, "Login successfully", true, authenticationResponse);
    } catch
      (final BadCredentialsException e) {

      AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
              .user(User.builder()
                      .userCode(user.getUserCode())
                      .build())
              .build();
      authenticationResponse.setUser(user);
      return new BaseResponse(Constants.ResponseCode.FAILURE, "You do not have permission to access the system", true, authenticationResponse);
    }



  }


}
