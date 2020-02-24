package com.app.service.impl;

import com.app.entity.LoginUser;
import com.app.repository.LoginUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DBUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginUserRepository userRepository;

    private final String ADMIN = "admin.role";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Username :.>>>>"+ username);
        List<LoginUser> users = userRepository.findByUsernameAndActive(username, true);
        System.out.println("Username :.>>>>"+ users);
        if (users != null && users.size() == 1) {
            return buildUserForAuthentication(users.get(0),getUserAuthority());
        }
        System.out.println("Throw exception");
        throw new UsernameNotFoundException("username not found");

    }
    private List<GrantedAuthority> getUserAuthority() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(ADMIN));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
    private UserDetails buildUserForAuthentication(LoginUser user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
