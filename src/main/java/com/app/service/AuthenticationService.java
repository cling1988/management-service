package com.app.service;

import com.app.model.TokenResponse;
import com.app.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    public String getTokenFromRequest(HttpServletRequest request);

    public boolean validateToken(String jwtToken);

    public String getUsernameFromToken(String jwtToken);

    public TokenResponse createToken(Authentication authentication);

    public LoginRequest getUserbyUsername(String username);

//    public ResponseEntity<?> createLoginUser(LoginRequest userModel);

}
