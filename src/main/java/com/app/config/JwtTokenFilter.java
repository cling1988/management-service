package com.app.config;

import com.app.model.ExceptionModel;
import com.app.service.AuthenticationService;
import com.app.service.impl.DBUserDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.sql.Timestamp;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationService jwtTokenService;

    @Autowired
    private DBUserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken  =jwtTokenService.getTokenFromRequest(request);
        try{
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
        } catch (RuntimeException e) {
            e.printStackTrace();
            ExceptionModel em = new ExceptionModel();
            em.setTimestamp(new Timestamp(System.currentTimeMillis()));
            em.setStatus(HttpStatus.UNAUTHORIZED.value());
            em.setError(HttpStatus.UNAUTHORIZED.toString());
            em.setMessage(e.getMessage());
            em.setPath(request.getPathInfo());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(convertObjectToJson(em));
        }
        filterChain.doFilter(request, response);
    }
    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
