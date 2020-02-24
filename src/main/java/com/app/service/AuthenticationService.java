package com.app.service;

import com.app.model.ProductModel;
import com.app.model.TokenModel;
import com.app.model.UserModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface AuthenticationService {
    public String getTokenFromRequest(HttpServletRequest request);

    public boolean validateToken(String jwtToken);

    public String getUsernameFromToken(String jwtToken);

    public TokenModel createToken(String username);

    public UserModel getUserbyUsername(String username);

    public ResponseEntity<?> createLoginUser(UserModel userModel);

}
