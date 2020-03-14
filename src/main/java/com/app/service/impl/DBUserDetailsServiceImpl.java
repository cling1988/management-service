package com.app.service.impl;

import com.app.common.UserPrincipal;
import com.app.entity.LoginUser;
import com.app.entity.Role;
import com.app.repository.LoginUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DBUserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LogManager.getLogger(DBUserDetailsServiceImpl.class);

    @Autowired
    private LoginUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LoginUser> users = userRepository.findByUsernameAndActive(username, true);

        if (users.isPresent()) {
            return buildUserForAuthentication(users.get(),buildRole(users.get().getRoles()),getUserAuthority(users.get().getRoles()));
        }
        throw new UsernameNotFoundException("username not found");

    }

    private String buildRole(Set<Role> roles){
        return roles.iterator().next().getName();
//        StringBuilder roleName = new StringBuilder();
//        if(roles.iterator().hasNext()) {
//            Role role = roles.iterator().next();
//            if(roleName.length()>0){
//                roleName.append("/");
//            }
//            roleName.append(role.getName());
//
//        }

//        return roleName.toString();

    }
    private List<GrantedAuthority> getUserAuthority(Set<Role> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(roles.iterator().hasNext()){
            Role role = roles.iterator().next();
            grantedAuthorities.addAll(role.getPermissions().stream().map(
                    d-> new SimpleGrantedAuthority(d.getName())).collect(Collectors.toList()));

        }
        return grantedAuthorities;
    }
    private UserDetails buildUserForAuthentication(LoginUser user,String role, List<GrantedAuthority> authorities) {
//        return new User(user.getUsername(), user.getPassword(), authorities);
        UserPrincipal principal= new UserPrincipal();
        principal.setUsername(user.getUsername());
        principal.setPassword(user.getPassword());
        principal.setRole(role);
        principal.setAuthorities(authorities);

        return principal;
    }
}
