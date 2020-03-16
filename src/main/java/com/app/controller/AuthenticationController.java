package com.app.controller;

import com.app.common.CommonKey;
import com.app.model.TokenResponse;
import com.app.model.LoginRequest;
import com.app.service.AuthenticationService;
import com.app.service.InitialDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path= CommonKey.URL_AUTHORIZATION)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private InitialDataService initialDataService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest user) {
        System.out.println("Login: "+user.toString());
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return authenticationService.createToken(authentication);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody LoginRequest user) {
//        LoginRequest userExists = authenticationService.getUserbyUsername(user.getUsername());
//        if (userExists != null) {
//            throw new BadCredentialsException("User with username: " + user.getUsername()+ " already exists");
//        }
//        return authenticationService.createLoginUser(user);
//    }

    @GetMapping("/init")
    public ResponseEntity<?> initData(){

        initialDataService.initData();

        return ResponseEntity.ok().build();
    }
}
