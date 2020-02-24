package com.app.service.impl;

import com.app.config.JwtTokenUtil;
import com.app.entity.LoginUser;
import com.app.model.TokenModel;
import com.app.model.UserModel;
import com.app.repository.LoginUserRepository;
import com.app.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LoginUserRepository loginUserRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public TokenModel createToken(String username) {
        return new TokenModel(username,jwtTokenUtil.generateToken(username));
    }

    @Override
    public UserModel getUserbyUsername(String username) {
        List<LoginUser> loginUsers = loginUserRepository.findByUsernameAndActive(username,true);
        if (loginUsers != null && loginUsers.size() == 1) {
            return modelMapper.map(loginUsers,UserModel.class);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> createLoginUser(UserModel userModel) {
        LoginUser u = new LoginUser();
        u.setActive(true);
        u.setUsername(userModel.getUsername());
        u.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        loginUserRepository.save(u);

        return ResponseEntity.ok(u.getUsername());
    }

    @Override
    public String getTokenFromRequest(HttpServletRequest request) {
        return jwtTokenUtil.resolveToken(request);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return jwtTokenUtil.getClaimFromToken(token, Claims::getSubject);
    }


    @Override
    public boolean validateToken(String token) {
        try {
            return !jwtTokenUtil.isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Expired or invalid JWT token");
        }
    }


}
