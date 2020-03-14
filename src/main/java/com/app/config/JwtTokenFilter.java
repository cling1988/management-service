package com.app.config;

import com.app.common.CommonKey;
import com.app.common.exception.TokenException;
import com.app.service.AuthenticationService;
import com.app.service.impl.DBUserDetailsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final Logger log = LogManager.getLogger(JwtTokenFilter.class);

    @Autowired
    private AuthenticationService jwtTokenService;

    @Autowired
    private DBUserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = jwtTokenService.getTokenFromRequest(request);
        try {
            if (jwtToken != null && jwtTokenService.validateToken(jwtToken)) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtTokenService.getUsernameFromToken(jwtToken));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (TokenException e) {
//            e.printStackTrace();
            log.error((e.getMessage()),e);
            request.setAttribute(CommonKey.EXCEPTION_MESSAGE,e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

}
