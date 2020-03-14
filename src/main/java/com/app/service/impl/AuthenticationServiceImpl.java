package com.app.service.impl;

import com.app.common.ApplicationType;
import com.app.common.LocalDatetimeUtil;
import com.app.common.RoleName;
import com.app.common.UserPrincipal;
import com.app.config.JwtTokenUtil;
import com.app.entity.LoginUser;
import com.app.entity.Role;
import com.app.model.TokenResponse;
import com.app.model.LoginRequest;
import com.app.repository.LoginUserRepository;
import com.app.repository.RoleRepository;
import com.app.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger log = LogManager.getLogger(AuthenticationServiceImpl.class);

    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LoginUserRepository loginUserRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public TokenResponse createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String username = userPrincipal.getUsername();
        String role = userPrincipal.getRole();
        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
        TokenResponse response = new TokenResponse(username, jwtTokenUtil.generateToken(username,role));
        log.info("Create token");
        for(GrantedAuthority a: authorities){
            log.info(a.getAuthority());
        }
        response.setPermissions(authorities.stream().map(a -> a.getAuthority()).collect(Collectors.toList()));
        response.setCurrentDatetime(LocalDatetimeUtil.nowDateTimeString());
        return response;
    }

    @Override
    public LoginRequest getUserbyUsername(String username) {
        Optional<LoginUser> loginUsers = loginUserRepository.findByUsernameAndActive(username, true);
        if (loginUsers.isPresent()) {
            return modelMapper.map(loginUsers.get(), LoginRequest.class);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> createLoginUser(LoginRequest userModel) {
        Optional<Role> role = roleRepository.findByName(RoleName.MANAGER.getValue());
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());

        LoginUser u = new LoginUser();
        u.setActive(true);
        u.setUsername(userModel.getUsername());
        u.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        u.setType(ApplicationType.WEB);
        u.setRoles(roles);

        loginUserRepository.save(u);

        return ResponseEntity.ok(u.getUsername());
    }

    @Override
    public String getTokenFromRequest(HttpServletRequest request) {
        return jwtTokenUtil.resolveToken(request);
    }

    @Override
    public String getUsernameFromToken(String token) {
        if (jwtTokenUtil.validateToken(token)) {
            return jwtTokenUtil.getClaimFromToken(token, Claims::getSubject);
        }
        return null;
    }


    @Override
    public boolean validateToken(String token) {
        return jwtTokenUtil.validateToken(token);
    }


}
