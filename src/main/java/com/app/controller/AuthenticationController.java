package com.app.controller;

import com.app.model.TokenModel;
import com.app.model.UserModel;
import com.app.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(path="/api/service/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public TokenModel login(@RequestBody UserModel user) {
        System.out.println("Login: "+user.toString());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return authenticationService.createToken(user.getUsername());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        System.out.println("Login: "+user.toString());
        UserModel userExists = authenticationService.getUserbyUsername(user.getUsername());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getUsername()+ " already exists");
        }
        return authenticationService.createLoginUser(user);
    }
}
